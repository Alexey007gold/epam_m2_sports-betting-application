package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.entity.WagerEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WagerService {

    List<Wager> getWagersByPlayer(Player player);

    List<Wager> getWagersByPlayerId(Integer id);

    List<Wager> getWagersByPlayerEmail(String email);

    boolean newWager(Integer playerId, Integer outcomeId, Double amount);

    boolean removeWager(Integer playerId, Integer wagerId);

    /**
     * Check player's wagers against actual outcomes
     * and increase player's balance
     *
     * @param playedEventsIds ids of events that are played (have results)
     * @return
     */
    Map<Integer, Double> processWagers(Set<Integer> playedEventsIds);

    double calculatePrize(WagerEntity wager);
}
