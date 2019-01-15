package com.epam.training.sportsbetting.domain.outcome;

import com.epam.training.sportsbetting.domain.bet.Bet;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public class Outcome {

    @PositiveOrZero
    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
