package com.epam.training.sportsbetting.domain.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Player extends User {

    @NotNull
    @Size(min = 3)
    private String name;
    @NotNull
    @Size(min = 3)
    private String accountNumber;
    @PositiveOrZero
    private double balance;
    @NotNull
    private Currency currency;
    @NotNull
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

    public Currency getCurrency() {
        return currency;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
