package com.epam.trainning.sportsbetting;

import com.epam.trainning.sportsbetting.domain.bet.Bet;
import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;
import com.epam.trainning.sportsbetting.domain.user.Currency;
import com.epam.trainning.sportsbetting.domain.user.Player;
import com.epam.trainning.sportsbetting.domain.wager.Wager;
import com.epam.trainning.sportsbetting.service.*;
import com.epam.trainning.sportsbetting.ui.ConsoleView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.trainning.sportsbetting.exception.ExceptionUtil.uncheck;

public class App {

    private static final DecimalFormat df = new DecimalFormat("###.##");
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private ConsoleView consoleView;
    private DataService dataService;
    private EventService eventService;
    private BetService betService;
    private PlayerService playerService;
    private WagerService wagerService;

    public static void main(String[] args) throws IOException, URISyntaxException {
        new App().run();
    }

    public App() throws IOException, URISyntaxException {
        this.consoleView = new ConsoleView();
        this.dataService = new DataService();
        this.eventService = new EventService(dataService);
        this.playerService = new PlayerService();
        this.wagerService = new WagerService();
        this.betService = new BetService(dataService, playerService);
    }

    private void run() {
        Player player = getPlayer();
        sayHello(player);

        Map<Outcome, Wager> userBets = gatherBets(player);

        eventService.playEvents();

        betService.processBets(userBets);

        displayResults();
        displayUserPrize(userBets);
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

    private void displayResults() {
        consoleView.write("Events results:\n");
        for (SportEvent event : dataService.getEvents()) {
            for (Outcome outcome : event.getResult().getOutcomes()) {
                consoleView.write(String.format(" - Event: %s The winner is Outcome %d, %s: %s%n",
                        event.getTitle(), dataService.getPossibleOutcomes().indexOf(outcome) + 1,
                        outcome.getBet(), outcome.getValue()));
            }
        }
        consoleView.write("\n");
    }

    private Map<Outcome, Wager> gatherBets(Player player) {
        Map<Outcome, Wager> userBets = new HashMap<>();
        Map<Outcome, SportEvent> outcomeToEventMap = new HashMap<>();
        dataService.getEvents()
                .forEach(event -> event.getBets()
                        .forEach(bet -> bet.getOutcomes()
                                .forEach(o -> outcomeToEventMap.put(o, bet.getEvent())))
                );

        while (true) {
            printBetMenu();

            Outcome chosenOutcome = getSelection(userBets);
            //change wager?
            if (chosenOutcome == null) return userBets;

            consoleView.write("How much do you want to bet on it? (q for quit)\n");
            double wage = getWage(player);
            if (wage == -1) return userBets;
            wage = uncheck(df::parse, df.format(wage)).doubleValue();

            Wager wager = new Wager(outcomeToEventMap.get(chosenOutcome),
                    player, chosenOutcome.getOutcomeOdds().get(0), wage,
                    player.getCurrency(), System.currentTimeMillis(), false, false);
            userBets.put(chosenOutcome, wager);

            playerService.decreasePlayerBalance(player, wage);

            consoleView.write(String.format("Your new balance is %s %s%n", df.format(player.getBalance()), player.getCurrency()));
            if (player.getBalance() <= 0) return userBets;
        }
    }

    private void printBetMenu() {
        consoleView.write("Please choose an outcome to bet on! (choose a number or press q for quit)\n");
        int outcomeNumber = 1;
        List<Bet> availableBets = dataService.getAvailableBets();
        for (Bet bet : availableBets) {
            for (Outcome outcome : bet.getOutcomes()) {
                OutcomeOdd odd = outcome.getActiveOdd();
                if (odd == null) continue;
                String betDescription = null;
                if (bet.getBetType() == Bet.BetType.GOAL) {
                    betDescription = "the number of scored goals will be " + outcome.getValue();
                } else if (bet.getBetType() == Bet.BetType.SCORE) {
                    betDescription = bet.getDescription() + " " + outcome.getValue();
                } else if (bet.getBetType() == Bet.BetType.WINNER) {
                    betDescription = "the winner will be " + outcome.getValue();
                }

                consoleView.write(String.format("%d: Bet on the %s sport event, %s. The odd on this is %s, valid from %s to %s%n",
                        outcomeNumber++, bet.getEvent().getTitle(), betDescription, odd.getValue(),
                        odd.getValidFrom().format(dtf), odd.getValidTo().format(dtf)));
            }
        }
        if (outcomeNumber == 1) {
            consoleView.write("No available bets found! Try again later. Bye!");
            System.exit(0);
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

    private Outcome getSelection(Map<Outcome, Wager> userBets) {
        String input;
        int selection;
        while (true) {
            input = consoleView.getStringInput();
            if (input.equalsIgnoreCase("q")) return null;
            try {
                selection = Integer.parseInt(input);
                if (selection < 1 || selection > dataService.getPossibleOutcomes().size()) {
                    throw new InputMismatchException();
                }
                Outcome chosenOutcome = dataService.getPossibleOutcomes().get(selection - 1);
                if (userBets.containsKey(chosenOutcome)) {
                    consoleView.write("You have already bet on this, try again:\n");
                    continue;
                }
                return chosenOutcome;
            } catch (Exception e) {
                consoleView.write("Try again:\n");
            }
        }
    }

    private void displayUserPrize(Map<Outcome, Wager> userBets) {
        if (userBets.isEmpty()) return;

        Player player = null;
        double prize = 0;
        for (Wager wager : userBets.values().stream()
                .filter(Wager::isWin)
                .collect(Collectors.toList())) {
            if (player == null) {
                player = wager.getPlayer();
            }

            consoleView.write(String.format("Your winner bet is Outcome %d %s%n",
                    dataService.getPossibleOutcomes().indexOf(wager.getOutcomeOdd().getOutcome()) + 1,
                    wager.getOutcomeOdd()));

            prize += wagerService.calculatePrize(wager);
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
