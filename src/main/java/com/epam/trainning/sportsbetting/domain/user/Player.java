package com.epam.trainning.sportsbetting.domain.user;

import java.time.LocalDate;

public class Player extends User {

    private String name;
    private String accountNumber;
    private double balance;
    private Currency currency;
    private LocalDate birthDate;

    public Player(String name, String accountNumber, double balance, Currency currency, LocalDate birthDate) {
        super(null, null, true);
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.birthDate = birthDate;
    }

    public Player(String email, char[] password, boolean enabled) {
        super(email, password, enabled);
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void decreaseBalance(double amount) {
        if (balance < amount) throw new IllegalArgumentException("Not enough money");

        balance -= amount;
    }

    public void increaseBalance(double amount) {
        balance += amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
