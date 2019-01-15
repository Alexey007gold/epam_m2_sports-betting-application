package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.SportEventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SportEventRepository extends AbstractRepository<SportEventEntity> {

    @Query("SELECT e from SportEventEntity e " +
            "WHERE e.startDate > CURRENT_TIMESTAMP")
    List<SportEventEntity> getFutureEvents();

    @Query("SELECT e from SportEventEntity e " +
        "JOIN BetEntity b ON b.event.id = e.id " +
        "JOIN OutcomeEntity o ON o.betId = b.id " +
        "WHERE o.id = :outcomeId")
    Optional<SportEventEntity> getByOutcomeId(@Param("outcomeId") Integer outcomeOddId);
}
