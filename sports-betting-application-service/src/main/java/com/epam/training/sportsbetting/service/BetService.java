package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.bet.Bet;

import java.util.List;

public interface BetService {

    List<Bet> getBetsByEventId(Integer eventId);
}
