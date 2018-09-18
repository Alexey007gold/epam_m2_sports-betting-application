package com.epam.trainning.sportsbetting.domain.sportevent;

import com.epam.trainning.sportsbetting.domain.outcome.Outcome;

import java.util.List;

public class Result {

    private List<Outcome> outcomes;

    public Result(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }
}
