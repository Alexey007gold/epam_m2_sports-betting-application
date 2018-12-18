package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;

import java.util.List;

public interface WagerService {

    List<Wager> getWagersByPlayer(Player player);

    List<Wager> getWagersByPlayerId(Integer id);

    List<Wager> getWagersByPlayerEmail(String email);

    boolean newWager(Integer playerId, Integer outcomeId, Double amount);

    boolean removeWager(Integer playerId, Integer wagerId);

    double calculatePrize(Wager wager);
}
