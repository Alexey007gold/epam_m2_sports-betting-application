package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.entity.OutcomeOddEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface OutcomeService {

    Outcome getOutcomeById(Integer id);

    OutcomeOdd getActiveOdd(List<OutcomeOdd> outcomeOdds);

    OutcomeOddEntity getActiveOddEntity(List<OutcomeOddEntity> outcomeOdds, LocalDateTime eventStartDateTime);
}
