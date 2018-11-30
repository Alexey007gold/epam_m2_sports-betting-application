package com.epam.training.sportsbetting;

import com.cookingfox.guava_preconditions.Preconditions;
import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.exception.ExceptionUtil;
import com.epam.training.sportsbetting.service.*;
import com.epam.training.sportsbetting.ui.ConsoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class App {

    private static final DecimalFormat df = new DecimalFormat("###.##");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private final ConsoleView consoleView;
    private final DataService dataService;
    private final EventService eventService;
    private final BetService betService;
    private final PlayerService playerService;
    private final WagerService wagerService;

    @Autowired
    public App(ConsoleView consoleView, DataService dataService,
               EventService eventService, BetService betService,
               PlayerService playerService, WagerService wagerService) {
        this.consoleView = consoleView;
        this.dataService = dataService;
        this.eventService = eventService;
        this.betService = betService;
        this.playerService = playerService;
        this.wagerService = wagerService;
    }

    private void run() {
        Player player = getPlayer();
        sayHello(player);

        List<Wager> userBets = gatherBets(player);

        //play only events that are not started yet
        List<SportEvent> events = dataService.getEvents().stream()
                .filter(e -> e.getStartDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
        eventService.playEvents(events);

        if (!userBets.isEmpty()) {
            Map<Player, Double> playerToPrizeMap = betService.processBets(userBets, events);
            displayResults(events);
            displayUserPrize(userBets, playerToPrizeMap.get(player));
        }
    }

    private Player getPlayer() {
        consoleView.write("Hi, what is your name?\n");
        String name = consoleView.getNonEmptyStringInput();
        consoleView.write("What is your account number?\n");
        String accountNumber = consoleView.getNonEmptyStringInput();
        consoleView.write("How much money do you have (more than 0)?\n");
        int balance = consoleView.getPositiveIntInput();
        String currencyPattern = getCurrencyPattern();
        consoleView.write(String.format("What is your currency? (%s)%n", currencyPattern));
        currencyPattern = currencyPattern + "|" + currencyPattern.toLowerCase();
        com.epam.training.sportsbetting.domain.user.Currency currency = com.epam.training.sportsbetting.domain.user.Currency.valueOf(consoleView.getStringInput(currencyPattern).toUpperCase());
        consoleView.write("When were you born? eg.:1990-02-03\n");

        LocalDate birthDate = consoleView.getDateInput();

        return playerService.registerPlayer(name, accountNumber, balance, currency, birthDate);
    }

    private void displayResults(List<SportEvent> eventsToDisplay) {
        consoleView.write("Events results:\n");
        for (SportEvent event : eventsToDisplay) {
            for (Outcome outcome : event.getResult().getOutcomes()) {
                consoleView.write(String.format(" - Event: %s The winner is Outcome %d, %s: %s%n",
                        event.getTitle(), dataService.getPossibleOutcomes().indexOf(outcome) + 1,
                        outcome.getBet(), outcome.getValue()));
            }
        }
        consoleView.write("\n");
    }

    private List<Wager> gatherBets(Player player) {
        List<Wager> userBets = new ArrayList<>();

        while (true) {
            if (player.getBalance() <= 0) return userBets;

            List<Outcome> outcomesAvailableForBetting = dataService.getPossibleOutcomes().stream()
                    .filter(o -> o.getActiveOdd() != null)
                    .collect(Collectors.toList());
            printBetMenu(outcomesAvailableForBetting);

            Outcome chosenOutcome = chooseOutcome(outcomesAvailableForBetting);
            if (chosenOutcome == null) return userBets;

            consoleView.write("How much do you want to bet on it? (q for quit)\n");
            double wage = getWage(player);
            if (wage == -1) return userBets;
            wage = ExceptionUtil.uncheck(df::parse, df.format(wage)).doubleValue();

            Wager wager = new Wager.Builder()
                    .withEvent(chosenOutcome.getBet().getEvent())
                    .withPlayer(player)
                    .withOutcomeOdd(chosenOutcome.getActiveOdd())
                    .withAmount(wage)
                    .withCurrency(player.getCurrency())
                    .withTimestamp(System.currentTimeMillis())
                    .build();

            userBets.add(wager);

            playerService.decreasePlayerBalance(player, wage);

            consoleView.write(String.format("Your new balance is %s %s%n", df.format(player.getBalance()), player.getCurrency()));
        }
    }

    private void printBetMenu(List<Outcome> outcomesAvailableForBetting) {
        consoleView.write("Please choose an outcome to bet on! (choose a number or press q for quit)\n");
        if (outcomesAvailableForBetting.isEmpty()) {
            consoleView.write("No available bets found! Try again later.");
        }

        for (int i = 0; i < outcomesAvailableForBetting.size(); i++) {
            Outcome outcome = outcomesAvailableForBetting.get(i);
            OutcomeOdd odd = outcome.getActiveOdd();
            Bet bet = outcome.getBet();

            String betDescription = null;
            if (bet.getBetType() == Bet.BetType.GOAL) {
                betDescription = "the number of scored goals will be " + outcome.getValue();
            } else if (bet.getBetType() == Bet.BetType.SCORE) {
                betDescription = bet.getDescription() + " " + outcome.getValue();
            } else if (bet.getBetType() == Bet.BetType.WINNER) {
                betDescription = "the winner will be " + outcome.getValue();
            }

            consoleView.write(String.format("%d: Bet on the %s sport event, %s. The odd on this is %s, valid from %s to %s%n",
                    i + 1, bet.getEvent().getTitle(), betDescription, odd.getValue(),
                    odd.getValidFrom().format(dtf), odd.getValidTo().format(dtf)));
        }
    }

    /**
     * Returns double value greater than 0 or -1 if user wishes to quit
     * @param player
     * @return
     */
    private double getWage(Player player) {
        while (true) {
            String input = consoleView.getStringInput();
            if (input.equalsIgnoreCase("q")) return -1;
            try {
                double wage = Double.parseDouble(input);

                Preconditions.checkArgument(wage > 0);

                if (!playerService.canMakeWager(player, wage)) {
                    consoleView.write(String.format("You don't have enough money, your balance is %s %s%n",
                            df.format(player.getBalance()), player.getCurrency()));
                    continue;
                }
                return wage;
            } catch (Exception e) {
                consoleView.write("Try again:\n");
            }
        }
    }

    /**
     * Lets the user choose an outcome from the list of possible outcomes
     * or null if user wishes to quit
     *
     * @param outcomesAvailableForBetting
     * @return
     */
    private Outcome chooseOutcome(List<Outcome> outcomesAvailableForBetting) {
        if (outcomesAvailableForBetting.isEmpty()) {
            return null;
        }

        String input;
        int selection;
        while (true) {
            input = consoleView.getStringInput();
            if (input.equalsIgnoreCase("q")) return null;
            try {
                selection = Integer.parseInt(input);

                Preconditions.checkArgument(selection > 0 && selection <= outcomesAvailableForBetting.size());

                return outcomesAvailableForBetting.get(selection - 1);
            } catch (Exception e) {
                consoleView.write("Try again:\n");
            }
        }
    }

    private void displayUserPrize(List<Wager> userBets, double prize) {
        if (userBets.isEmpty()) return;

        Player player = null;
        for (Wager wager : userBets.stream()
                .filter(Wager::isWinner)
                .collect(Collectors.toList())) {
            if (player == null) {
                player = wager.getPlayer();
            }

            consoleView.write(String.format("Your winner bet is Outcome %d %s; wage: %s; prize for that: %s%n",
                    dataService.getPossibleOutcomes().indexOf(wager.getOutcomeOdd().getOutcome()) + 1,
                    wager.getOutcomeOdd(),
                    df.format(wager.getAmount()),
                    df.format(wagerService.calculatePrize(wager))));
        }
        if (player == null && prize > 0) {
            throw new IllegalStateException("Prize cannot be positive when no wager is winner");
        }

        if (prize > 0) {
            consoleView.write(String.format("You have won %s %s%n", df.format(prize), player.getCurrency()));
            consoleView.write(String.format("Your new balance is %s %s%n", df.format(player.getBalance()), player.getCurrency()));
        } else {
            consoleView.write("Unfortunately you haven't won anything");
        }
    }

    private void sayHello(Player player) {
        consoleView.write(String.format("Hello %s!%n", player.getName()));
        consoleView.write(String.format("Your balance is %s %s%n", df.format(player.getBalance()), player.getCurrency()));
    }

    private String getCurrencyPattern() {
        return Arrays.stream(Currency.values()).map(Enum::toString).collect(Collectors.joining("|"));
    }
}
