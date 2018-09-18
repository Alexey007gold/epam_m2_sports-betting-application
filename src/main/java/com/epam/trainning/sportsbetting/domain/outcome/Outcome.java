package com.epam.trainning.sportsbetting.domain.outcome;

import java.util.List;

public class Outcome {

    private String value;
    private List<OutcomeOdd> outcomeOdds;

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
}
