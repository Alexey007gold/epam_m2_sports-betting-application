package com.epam.trainning.sportsbetting.domain.sportevent;

import com.epam.trainning.sportsbetting.domain.bet.Bet;

import java.time.LocalDateTime;
import java.util.List;

public class FootballSportEvent extends SportEvent {

    public FootballSportEvent() {
    }

    public FootballSportEvent(String title, LocalDateTime startDate, LocalDateTime endDate, List<Bet> bets) {
        super(title, startDate, endDate, bets);
    }
}
