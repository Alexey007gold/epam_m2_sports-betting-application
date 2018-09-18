package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.user.Currency;
import com.epam.trainning.sportsbetting.domain.user.Player;
import com.epam.trainning.sportsbetting.ui.ConsoleView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PlayerService {

    private ConsoleView consoleView;

    public PlayerService(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public Player registerPlayer() {
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
        LocalDate birthDate = LocalDate.parse(consoleView.getStringInput("\\d{4}-\\d{2}-\\d{2}"));

        return new Player(name, accountNumber, balance, currency, birthDate);
    }

    private String getCurrencyPattern() {
        return Arrays.stream(Currency.values()).map(Enum::toString).collect(Collectors.joining("|"));
    }
}
