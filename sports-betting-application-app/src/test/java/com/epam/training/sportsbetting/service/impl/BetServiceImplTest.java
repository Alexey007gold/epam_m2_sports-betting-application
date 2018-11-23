package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.FootballSportEvent;
import com.epam.training.sportsbetting.domain.sportevent.Result;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.TestUtils;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BetServiceImplTest {

    @Mock
    private PlayerService playerService;

    private BetServiceImpl betService = BetServiceImpl.getInstance();

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() throws Exception {
        Constructor<BetServiceImpl> declaredConstructor =
            (Constructor<BetServiceImpl>) BetServiceImpl.class.getDeclaredConstructors()[0];
        declaredConstructor.setAccessible(true);
        TestUtils.setPrivateFinalField(BetServiceImpl.class, "INSTANCE", declaredConstructor.newInstance());
    }

    @Test
    void shouldReturnCorrectResultOnProcessBets() {
        betService.setPlayerService(playerService);
        Player player = new Player.Builder()
            .withName("John")
            .withBalance(100)
            .withAccountNumber("1111")
            .withCurrency(Currency.EUR)
            .build();

        SportEvent event = getEvent();
        Outcome outcome1 = event.getBets().get(0).getOutcomes().get(0);
        Outcome outcome2 = event.getBets().get(0).getOutcomes().get(1);
        event.setResult(new Result(Collections.singletonList(outcome2)));
        List<Wager> wagers = Arrays.asList(
            new Wager.Builder()
                .withEvent(event)
                .withPlayer(player)
                .withOutcomeOdd(outcome1.getOutcomeOdds().get(0))
                .withAmount(5)
                .withCurrency(player.getCurrency())
                .withTimestamp(System.currentTimeMillis())
                .build(),
            new Wager.Builder()
                .withEvent(event)
                .withPlayer(player)
                .withOutcomeOdd(outcome2.getOutcomeOdds().get(0))
                .withAmount(5)
                .withCurrency(player.getCurrency())
                .withTimestamp(System.currentTimeMillis())
                .build());

        Map<Player, Double> playerPrizeMap = betService.processBets(wagers, Collections.singletonList(event));

        assertEquals(8.5, (double) playerPrizeMap.get(player));
        verify(playerService).increasePlayerBalance(any(Player.class), eq(8.5));
    }

    @Test
    void shouldThrowAnExceptionOnProcessBets() {
        assertThrows(IllegalArgumentException.class, () -> betService.processBets(null, Collections.emptyList()));
    }

    @Test
    void shouldReturnEmptyMapOnProcessBets() {
        betService.setPlayerService(playerService);
        betService.processBets(Collections.emptyList(), Collections.emptyList());
    }

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