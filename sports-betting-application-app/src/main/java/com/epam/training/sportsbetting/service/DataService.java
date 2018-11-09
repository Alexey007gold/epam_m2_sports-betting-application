package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;

import java.util.List;

public interface DataService {
    List<SportEvent> getEvents();

    List<Bet> getAvailableBets();

    List<Outcome> getPossibleOutcomes();
}
