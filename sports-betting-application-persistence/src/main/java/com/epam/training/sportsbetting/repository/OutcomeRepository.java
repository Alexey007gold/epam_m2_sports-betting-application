package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.OutcomeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OutcomeRepository extends AbstractRepository<OutcomeEntity> {

    @Query("SELECT o FROM OutcomeEntity o " +
            "JOIN BetEntity b ON o.betId = b.id " +
            "JOIN SportEventEntity e ON b.event.id = e.id " +
            "WHERE e.id = :eventId AND o.id = :outcomeId")
    OutcomeEntity findByIdAndEventId(@Param("outcomeId") Integer outcomeId, @Param("eventId") Integer eventId);
}
