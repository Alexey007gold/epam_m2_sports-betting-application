package com.epam.training.sportsbetting.domain.wager;

import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;

public class Wager {

    private SportEvent event;
    private Player player;
    private OutcomeOdd outcomeOdd;
    private double amount;
    private Currency currency;
    private long timestamp;
    private boolean processed;
    private boolean winner;

    private Wager() {
    }

    private Wager(SportEvent event, Player player, OutcomeOdd outcomeOdd,
                  double amount, Currency currency, long timestamp,
                  boolean processed, boolean winner) {
        this.event = event;
        this.player = player;
        this.outcomeOdd = outcomeOdd;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.processed = processed;
        this.winner = winner;
    }

    public SportEvent getEvent() {
        return event;
    }

    public void setEvent(SportEvent event) {
        this.event = event;
    }

    public Player getPlayer() {
        return player;
    }

    public OutcomeOdd getOutcomeOdd() {
        return outcomeOdd;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isProcessed() {
        return processed;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setOutcomeOdd(OutcomeOdd outcomeOdd) {
        this.outcomeOdd = outcomeOdd;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }


    public static class Builder {

        private Wager wager;

        public Builder() {
            this.wager = new Wager();
        }

        public Builder withSportEvent(SportEvent sportEvent) {
            wager.setEvent(sportEvent);
            return this;
        }

        public Builder withEvent(SportEvent event) {
            wager.event = event;
            return this;
        }

        public Builder withPlayer(Player player) {
            wager.player = player;
            return this;
        }

        public Builder withOutcomeOdd(OutcomeOdd outcomeOdd) {
            wager.outcomeOdd = outcomeOdd;
            return this;
        }

        public Builder withAmount(double amount) {
            wager.amount = amount;
            return this;
        }

        public Builder withCurrency(Currency currency) {
            wager.currency = currency;
            return this;
        }

        public Builder withTimestamp(long timestamp) {
            wager.timestamp = timestamp;
            return this;
        }

        public Builder withProcessed(boolean processed) {
            wager.processed = processed;
            return this;
        }

        public Builder withWinner(boolean winner) {
            wager.winner = winner;
            return this;
        }

        public Wager build() {
            return wager;
        }
    }
}
