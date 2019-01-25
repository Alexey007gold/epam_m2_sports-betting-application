package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.BetEntity;

import java.util.List;

public interface BetRepository extends AbstractRepository<BetEntity> {

    List<BetEntity> findByEventId(Integer id);
}
