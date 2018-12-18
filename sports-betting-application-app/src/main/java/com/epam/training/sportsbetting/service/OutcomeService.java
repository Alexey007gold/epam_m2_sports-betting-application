package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.outcome.Outcome;

public interface OutcomeService {

    Outcome getOutcomeById(Integer id);
}
