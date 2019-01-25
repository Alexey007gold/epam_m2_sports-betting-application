package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.WagerEntity;

import java.util.List;

public interface WagerRepository extends AbstractRepository<WagerEntity> {

    List<WagerEntity> findByPlayerId(int id);

    List<WagerEntity> findByPlayerEmail(String email);

    int deleteByIdAndPlayerId(int wagerId, int playerId);

}
