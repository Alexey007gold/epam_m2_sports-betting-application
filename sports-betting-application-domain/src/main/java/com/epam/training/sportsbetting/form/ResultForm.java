package com.epam.training.sportsbetting.form;

import lombok.Data;

import java.util.List;

@Data
public class ResultForm {

    private List<OutcomeForm> outcomes;

    public ResultForm(List<OutcomeForm> outcomes) {
        this.outcomes = outcomes;
    }

    public List<OutcomeForm> getOutcomes() {
        return outcomes;
    }
}