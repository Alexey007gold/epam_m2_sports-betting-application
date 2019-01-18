package com.epam.training.sportsbetting.domain.sportevent;

import com.epam.training.sportsbetting.domain.outcome.Outcome;

import java.util.List;

public class Result {

    private List<Outcome> outcomes;

    public Result() {
    }

    public Result(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }
}
