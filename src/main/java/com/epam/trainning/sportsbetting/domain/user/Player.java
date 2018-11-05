package com.epam.trainning.sportsbetting.domain.user;

import java.time.LocalDate;

public class Player extends User {

    private String name;
    private String accountNumber;
    private double balance;
    private Currency currency;
    private LocalDate birthDate;

    private Player() {
    }

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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public static class Builder extends User.Builder<Builder> {

        private Player player;

        public Builder() {
            this.user = this.player = new Player();
        }

        public Builder withName(String name) {
            player.name = name;
            return this;
        }

        public Builder withAccountNumber(String accountNumber) {
            player.accountNumber = accountNumber;
            return this;
        }

        public Builder withCurrency(Currency currency) {
            player.currency = currency;
            return this;
        }

        public Builder withBalance(double balance) {
            player.balance = balance;
            return this;
        }

        public Builder withBirthDate(LocalDate birthDate) {
            player.birthDate = birthDate;
            return self();
        }

        public Player build() {
            return player;
        }
    }
}
