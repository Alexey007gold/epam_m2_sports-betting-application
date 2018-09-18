package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.bet.Bet;
import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.trainning.sportsbetting.domain.user.Player;
import com.epam.trainning.sportsbetting.domain.wager.Wager;
import com.epam.trainning.sportsbetting.ui.ConsoleView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class BetService {

    private ConsoleView consoleView;
    private DataService dataService;

    private DecimalFormat df = new DecimalFormat("###.##");

    public BetService(ConsoleView consoleView, DataService dataService) {
        this.consoleView = consoleView;
        this.dataService = dataService;
    }

    public Map<Outcome, Wager> gatherBets(Player player) {
        Map<Outcome, Wager> userBets = new HashMap<>();
        while (true) {
            printBetMenu();

            int selection = getSelection() - 1;
            if (selection < 0) return userBets;
            List<Outcome> possibleOutcomes = dataService.getPossibleOutcomes();
            while (userBets.containsKey(possibleOutcomes.get(selection))) {
                consoleView.write("You have already bet on this, try again:\n");
                selection = getSelection() - 1;
                if (selection == -1) return userBets;
            }

            consoleView.write("How much do you want to bet on it? (q for quit)\n");
            double wage = getWage(player);
            if (wage == -1) return userBets;
            try {
                wage = df.parse(df.format(wage)).doubleValue();
            } catch (ParseException e) {
                throw new IllegalStateException();
            }


            Wager wager = new Wager(player, possibleOutcomes.get(selection).getOutcomeOdds().get(0), wage, player.getCurrency(),
                    System.currentTimeMillis(), false, false);
            userBets.put(possibleOutcomes.get(selection), wager);
            player.decreaseBalance(wage);

            consoleView.write(String.format("Your new balance is %s %s%n", df.format(player.getBalance()), player.getCurrency()));
            if (player.getBalance() <= 0) return userBets;
        }
    }

    public void processBets(Map<Outcome, Wager> userBets) {
        if (userBets.isEmpty()) return;
        AtomicReference<Player> player = new AtomicReference<>();
        AtomicReference<Double> prize = new AtomicReference<>(.0);
        dataService.getEvents().forEach(e -> e.getResult().getOutcomes().forEach(o -> {
            Wager wager = userBets.get(o);
            if (wager != null) {
                if (player.get() == null) {
                    player.set(wager.getPlayer());
                }
                wager.setProcessed(true);
                wager.setWin(true);
                String outcomeInfo = "[" +
                        "value=" + o.getValue() +
                        ", outcomeOdds=" + wager.getOutcomeOdd().getValue() +
                        " and valid from " + localDateTimeToString(wager.getOutcomeOdd().getValidFrom()) +
                        " to " + localDateTimeToString(wager.getOutcomeOdd().getValidTo() == null ? e.getStartDate() : wager.getOutcomeOdd().getValidTo()) +
                        ']';
                consoleView.write(String.format("The winner is Outcome %d %s%n",
                        dataService.getPossibleOutcomes().indexOf(o) + 1, outcomeInfo));
                prize.updateAndGet(v -> v + wager.getAmount() * wager.getOutcomeOdd().getValue());
            }
        }));
        if (player.get() != null) {
            player.get().increaseBalance(prize.get());
        }
    }

    private void printBetMenu() {
        consoleView.write("Please choose an outcome to bet on! (choose a number or press q for quit)\n");
        AtomicInteger outcomeNumber = new AtomicInteger(1);
        List<Bet> availableBets = dataService.getAvailableBets();
        availableBets.forEach(bet -> bet.getOutcomes().forEach(outcome -> {
            OutcomeOdd odd = outcome.getOutcomeOdds().get(0);
            String betDescription = null;
            if (bet.getBetType() == Bet.BetType.GOAL) {
                betDescription = "the number of scored goals will be " + outcome.getValue();
            } else if (bet.getBetType() == Bet.BetType.SCORE) {
                betDescription = bet.getDescription();
            } else if (bet.getBetType() == Bet.BetType.WINNER) {
                betDescription = "the winner will be " + outcome.getValue();
            }

            LocalDateTime validTo = odd.getValidTo() == null ? bet.getEvent().getStartDate() : odd.getValidTo();
            consoleView.write(String.format("%d: Bet on the %s sport event, %s. The odd on this is %s, valid from %s to %s%n",
                    outcomeNumber.getAndIncrement(), bet.getEvent().getTitle(), betDescription, odd.getValue(),
                    localDateTimeToString(odd.getValidFrom()), localDateTimeToString(validTo)));
        }));
    }

    private String localDateTimeToString(LocalDateTime validFrom) {
        return validFrom.toString().replace('T', ' ');
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
                if (wage > player.getBalance()) {
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

    private int getSelection() {
        String input;
        int selection;
        while (true) {
            input = consoleView.getStringInput();
            if (input.equalsIgnoreCase("q")) return -1;
            try {
                selection = Integer.parseInt(input);
                if (selection < 1 || selection > dataService.getPossibleOutcomes().size()) {
                    throw new InputMismatchException();
                }
                return selection;
            } catch (Exception e) {
                consoleView.write("Try again:\n");
            }
        }
    }
}
