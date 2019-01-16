package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.FootballSportEvent;
import com.epam.training.sportsbetting.domain.sportevent.Result;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.sportevent.TennisSportEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void shouldSetRandomizedResultsToEventsOnPlayEvents() {
        List<SportEvent> events = getEvents();
        //check that the results are unknown yet
        events.forEach(e -> assertNull(e.getResult()));

        List<List<Result>> results = new ArrayList<>();
        //play events 10 times
        for (int i = 0; i < 10; i++) {
            eventService.playEvents(events);

            events.forEach(e -> {
                assertNotNull(e.getResult());
                assertEquals(e.getBets().size(), e.getResult().getOutcomes().size());
            });

            results.add(events.stream().map(SportEvent::getResult).collect(Collectors.toList()));
        }

        //check that there is difference between results
        boolean different = false;
        for (int i = 0; i < results.size() - 1; i++) {
            if (!results.get(i).equals(results.get(i + 1))) {
                different = true;
                break;
            }
        }
        assertTrue(different);
    }

    private List<SportEvent> getEvents() {
        SportEvent event1 = new FootballSportEvent("Southampton v Bournemoth",
            LocalDateTime.parse("2016-10-27T19:00"), LocalDateTime.parse("2016-10-27T21:00"), null);

        Outcome outcome1 = new Outcome("Southampton");
        OutcomeOdd odd1 = new OutcomeOdd(outcome1, 4,
            LocalDateTime.parse("2016-09-27T19:00"), LocalDateTime.parse("2016-09-30T18:59"));
        OutcomeOdd odd2 = new OutcomeOdd(outcome1, 5,
            LocalDateTime.parse("2016-09-30T19:00"), null);
        outcome1.setOutcomeOdds(Arrays.asList(odd1, odd2));

        Outcome outcome2 = new Outcome("Bournemoth");
        OutcomeOdd odd3 = new OutcomeOdd(outcome2, 1.7,
            LocalDateTime.parse("2016-09-27T19:00"), LocalDateTime.parse("2016-09-30T18:59"));
        OutcomeOdd odd4 = new OutcomeOdd(outcome2, 1.5,
            LocalDateTime.parse("2016-09-30T19:00"), null);
        outcome2.setOutcomeOdds(Arrays.asList(odd3, odd4));

        Outcome outcome3 = new Outcome("Draw");
        OutcomeOdd odd5 = new OutcomeOdd(outcome3, 3,
            LocalDateTime.parse("2016-09-27T19:00"), LocalDateTime.parse("2016-09-30T18:59"));
        OutcomeOdd odd6 = new OutcomeOdd(outcome3, 3.5,
            LocalDateTime.parse("2016-09-30T19:00"), null);
        outcome3.setOutcomeOdds(Arrays.asList(odd5, odd6));

        Bet bet1 = new Bet(event1, "", Arrays.asList(outcome1, outcome2, outcome3), Bet.BetType.WINNER);


        Outcome outcome4 = new Outcome("0");
        OutcomeOdd odd7 = new OutcomeOdd(outcome4, 1.75,
            LocalDateTime.parse("2016-09-27T19:00"), null);
        outcome4.setOutcomeOdds(Arrays.asList(odd7));

        Outcome outcome5 = new Outcome("1");
        OutcomeOdd odd8 = new OutcomeOdd(outcome5, 1.25,
            LocalDateTime.parse("2016-09-27T19:00"), null);
        outcome5.setOutcomeOdds(Arrays.asList(odd8));

        Outcome outcome6 = new Outcome(">=2");
        OutcomeOdd odd9 = new OutcomeOdd(outcome6, 1.05,
            LocalDateTime.parse("2016-09-27T19:00"), null);
        outcome6.setOutcomeOdds(Arrays.asList(odd9));

        Bet bet2 = new Bet(event1, "", Arrays.asList(outcome4, outcome5, outcome6), Bet.BetType.GOAL);


        Outcome outcome7 = new Outcome("0");
        OutcomeOdd odd10 = new OutcomeOdd(outcome7, 1.05,
            LocalDateTime.parse("2016-09-27T19:00"), null);
        outcome7.setOutcomeOdds(Arrays.asList(odd10));
        Outcome outcome8 = new Outcome("1");
        OutcomeOdd odd11 = new OutcomeOdd(outcome8, 1.80,
            LocalDateTime.parse("2016-09-27T19:00"), null);
        outcome8.setOutcomeOdds(Arrays.asList(odd11));
        Outcome outcome9 = new Outcome("2");
        OutcomeOdd odd12 = new OutcomeOdd(outcome9, 3.15,
            LocalDateTime.parse("2016-09-27T19:00"), null);
        outcome9.setOutcomeOdds(Arrays.asList(odd12));

        Bet bet3 = new Bet(event1, "scores of Victor Wanyama", Arrays.asList(outcome7, outcome8, outcome9), Bet.BetType.SCORE);
        event1.setBets(Arrays.asList(bet1));
        event1.setBets(Arrays.asList(bet2));
        event1.setBets(Arrays.asList(bet3));


        SportEvent event2 = new TennisSportEvent("Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round",
            LocalDateTime.parse("2016-08-10T19:00"), LocalDateTime.parse("2016-08-10T22:00"), null);

        Outcome outcome10 = new Outcome("Rafael Nadal");
        OutcomeOdd odd13 = new OutcomeOdd(outcome9, 1.01,
            LocalDateTime.parse("2016-01-01T00:00"), null);
        outcome10.setOutcomeOdds(Arrays.asList(odd13));
        Outcome outcome11 = new Outcome("Alexander Zverev");
        OutcomeOdd odd14 = new OutcomeOdd(outcome9, 1.7,
            LocalDateTime.parse("2016-01-01T00:00"), null);
        outcome11.setOutcomeOdds(Arrays.asList(odd14));

        Bet bet4 = new Bet(event2, "", Arrays.asList(outcome10, outcome11), Bet.BetType.WINNER);
        event2.setBets(Arrays.asList(bet4));


        return Arrays.asList(event1, event2);
    }
}