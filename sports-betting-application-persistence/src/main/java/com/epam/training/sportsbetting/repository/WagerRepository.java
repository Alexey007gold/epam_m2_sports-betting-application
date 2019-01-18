package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.WagerEntity;

import java.util.List;
import java.util.Set;

public interface WagerRepository extends AbstractRepository<WagerEntity> {

    List<WagerEntity> findByPlayerId(int id);

    List<WagerEntity> findByPlayerEmail(String email);

    int deleteByIdAndPlayerId(int wagerId, int playerId);

    @Query("SELECT w FROM WagerEntity w " +
            "WHERE w.event.id IN :eventIds AND w.processed = false")
    List<WagerEntity> findByEventIdAndNotProcessed(@Param("eventIds") Set<Integer> eventsIds);
}
