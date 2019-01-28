package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.OutcomeEntity;

public interface OutcomeRepository extends AbstractRepository<OutcomeEntity> {

    OutcomeEntity findByIdAndBetEventId(Integer outcomeId, Integer eventId);
}
