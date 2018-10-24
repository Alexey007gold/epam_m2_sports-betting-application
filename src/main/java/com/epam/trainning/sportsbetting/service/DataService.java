package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.bet.Bet;
import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;

import java.util.List;

public interface DataService {
    List<SportEvent> getEvents();

    List<Bet> getAvailableBets();

    List<Outcome> getPossibleOutcomes();
}
