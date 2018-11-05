package com.epam.trainning.sportsbetting;

import com.epam.trainning.sportsbetting.domain.bet.Bet;
import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;
import com.epam.trainning.sportsbetting.domain.user.Currency;
import com.epam.trainning.sportsbetting.domain.user.Player;
import com.epam.trainning.sportsbetting.domain.wager.Wager;
import com.epam.trainning.sportsbetting.service.*;
import com.epam.trainning.sportsbetting.service.impl.*;
import com.epam.trainning.sportsbetting.ui.ConsoleView;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.trainning.sportsbetting.exception.ExceptionUtil.uncheck;

public class App {

    private static final DecimalFormat df = new DecimalFormat("###.##");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private ConsoleView consoleView;
    private DataService dataService;
    private EventService eventService;
    private BetService betService;
    private PlayerService playerService;
    private WagerService wagerService;

    public static void main(String[] args) {
        new App().run();
    }

    public App() {
        this.consoleView = new ConsoleView();
        this.dataService = DataServiceImpl.getInstance();
        this.eventService = EventServiceImpl.getInstance();
        this.playerService = PlayerServiceImpl.getInstance();
        this.wagerService = WagerServiceImpl.getInstance();
        this.betService = BetServiceImpl.getInstance();
        ((BetServiceImpl) this.betService).setPlayerService(playerService);
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

        Map<Player, Double> playerToPrizeMap = betService.processBets(userBets, events);
        displayResults(events);
        displayUserPrize(userBets, playerToPrizeMap.get(player));
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
        Currency currency = Currency.valueOf(consoleView.getStringInput(currencyPattern).toUpperCase());
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
            wage = uncheck(df::parse, df.format(wage)).doubleValue();

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

    private double getWage(Player player) {
        while (true) {
            String input = consoleView.getStringInput();
            if (input.equalsIgnoreCase("q")) return -1;
            try {
                double wage = Double.parseDouble(input);
                if (wage < 0) {
                    throw new InputMismatchException();
                }
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
     * Lets the user to choose an outcome from the list of possible outcomes
     * User cannot choose
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
                if (selection < 1 || selection > outcomesAvailableForBetting.size()) {
                    throw new InputMismatchException();
                }
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
