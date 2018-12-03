package com.epam.training.sportsbetting.domain.outcome;

import com.epam.training.sportsbetting.domain.bet.Bet;

import java.time.LocalDateTime;
import java.util.List;

public class Outcome {

    private String value;
    private List<OutcomeOdd> outcomeOdds;
    private Bet bet;

    public Outcome() {
    }

    public Outcome(String value) {
        this.value = value;
    }

    public Outcome(String value, List<OutcomeOdd> outcomeOdds) {
        this.value = value;
        this.outcomeOdds = outcomeOdds;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public List<OutcomeOdd> getOutcomeOdds() {
        return outcomeOdds;
    }

    public void setOutcomeOdds(List<OutcomeOdd> outcomeOdds) {
        this.outcomeOdds = outcomeOdds;
    }

    public OutcomeOdd getActiveOdd() {
        if (outcomeOdds == null) return null;

        LocalDateTime now = LocalDateTime.now();
        for (OutcomeOdd outcomeOdd : outcomeOdds) {
            LocalDateTime validFrom = outcomeOdd.getValidFrom();
            LocalDateTime validTo = outcomeOdd.getValidTo();
            if ((validFrom.isBefore(now) || validFrom.isEqual(now)) &&
                    (validTo.isAfter(now) || validFrom.isEqual(now))) {
                return outcomeOdd;
            }
        }
        return null;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    @Override
    public String toString() {
        return "Outcome{" +
            "value='" + value + '\'' +
            ", outcomeOdds=" + outcomeOdds +
            ", bet=" + bet +
            '}';
    }
}
