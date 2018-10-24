package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;
import com.epam.trainning.sportsbetting.domain.user.Player;
import com.epam.trainning.sportsbetting.domain.wager.Wager;

import java.util.List;
import java.util.Map;

public interface BetService {
    /**
     * Check user's bets against actual outcomes
     * and increase user's balance
     * @param userBets
     * @param events
     * @return
     */
    Map<Player, Double> processBets(List<Wager> userBets, List<SportEvent> events);
}
