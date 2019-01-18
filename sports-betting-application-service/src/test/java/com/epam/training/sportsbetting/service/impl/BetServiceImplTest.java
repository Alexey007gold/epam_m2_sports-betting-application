package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.FootballSportEvent;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.service.PlayerService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class BetServiceImplTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private BetServiceImpl betService;

    private SportEvent getEvent() {
        SportEvent event1 = new FootballSportEvent("Southampton v Bournemoth",
            LocalDateTime.parse("2019-10-27T19:00"), LocalDateTime.parse("2019-10-27T21:00"), null);

        Outcome outcome1 = new Outcome("Southampton");
        OutcomeOdd odd1 = new OutcomeOdd(outcome1, 4,
            LocalDateTime.parse("2018-09-23T19:00"), LocalDateTime.parse("2018-09-30T18:59"));
        OutcomeOdd odd2 = new OutcomeOdd(outcome1, 5,
            LocalDateTime.parse("2018-09-30T19:00"), LocalDateTime.parse("2019-10-27T19:00"));
        outcome1.setOutcomeOdds(Arrays.asList(odd1, odd2));

        Outcome outcome2 = new Outcome("Bournemoth");
        OutcomeOdd odd3 = new OutcomeOdd(outcome2, 1.7,
            LocalDateTime.parse("2018-09-23T19:00"), LocalDateTime.parse("2018-09-30T18:59"));
        OutcomeOdd odd4 = new OutcomeOdd(outcome2, 1.5,
            LocalDateTime.parse("2018-09-30T19:00"), LocalDateTime.parse("2019-10-27T19:00"));
        outcome2.setOutcomeOdds(Arrays.asList(odd3, odd4));

        Bet bet1 = new Bet(event1, "", Arrays.asList(outcome1, outcome2), Bet.BetType.WINNER);

        event1.setBets(Collections.singletonList(bet1));
        return event1;
    }
}