package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.SportEventEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SportEventRepository extends AbstractRepository<SportEventEntity> {

    @Query("SELECT e from SportEventEntity e " +
            "WHERE e.startDate > CURRENT_TIMESTAMP")
    List<SportEventEntity> getFutureEvents();

    Optional<SportEventEntity> getByBetsOutcomesId(Integer outcomeOddId);
}
