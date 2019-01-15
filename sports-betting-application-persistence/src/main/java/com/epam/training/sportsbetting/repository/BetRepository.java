package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.BetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BetRepository extends AbstractRepository<BetEntity> {

    @Query("SELECT b FROM BetEntity b " +
        "JOIN SportEventEntity s ON b.event.id = s.id " +
        "WHERE s.id = :eventId")
    List<BetEntity> findByEventId(@Param("eventId") Integer id);
}
