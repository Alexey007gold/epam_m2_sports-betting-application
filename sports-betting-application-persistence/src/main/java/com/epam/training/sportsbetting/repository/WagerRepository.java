package com.epam.training.sportsbetting.repository;

import com.epam.training.sportsbetting.entity.WagerEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WagerRepository extends AbstractRepository<WagerEntity> {

    List<WagerEntity> findByPlayerId(int id);

    List<WagerEntity> findByPlayerEmail(String email);

    @Modifying
    @Query("DELETE FROM WagerEntity w WHERE w.id = :wagerId AND w.player.id = :playerId")
    int deleteByPlayerIdAndWagerId(@Param("playerId") int playerId, @Param("wagerId") int wagerId);

}
