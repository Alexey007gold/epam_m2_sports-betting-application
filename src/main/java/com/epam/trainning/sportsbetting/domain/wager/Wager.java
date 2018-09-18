package com.epam.trainning.sportsbetting.domain.wager;

import com.epam.trainning.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;
import com.epam.trainning.sportsbetting.domain.user.Currency;
import com.epam.trainning.sportsbetting.domain.user.Player;

public class Wager {

    private SportEvent event;
    private Player player;
    private OutcomeOdd outcomeOdd;
    private double amount;
    private Currency currency;
    private long timestamp;
    private boolean processed;
    private boolean win;

    public Wager(SportEvent event, Player player, OutcomeOdd outcomeOdd,
                 double amount, Currency currency, long timestamp,
                 boolean processed, boolean win) {
        this.event = event;
        this.player = player;
        this.outcomeOdd = outcomeOdd;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.processed = processed;
        this.win = win;
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

    public boolean isWin() {
        return win;
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

    public void setWin(boolean win) {
        this.win = win;
    }
}
