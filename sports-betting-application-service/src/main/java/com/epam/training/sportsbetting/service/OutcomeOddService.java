package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.form.AddOutcomeOddForm;

import java.util.List;

public interface OutcomeOddService {

    List<OutcomeOdd> addOutcomeOdds(List<AddOutcomeOddForm> outcomeOddFormList);
}
