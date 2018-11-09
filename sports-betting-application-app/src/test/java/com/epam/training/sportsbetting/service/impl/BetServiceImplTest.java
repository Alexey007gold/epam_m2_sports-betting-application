package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.Result;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.DataService;
import com.epam.training.sportsbetting.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BetServiceImplTest {

    @Mock
    private PlayerService playerService;

    private BetServiceImpl betService = BetServiceImpl.getInstance();
    private DataService dataService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() throws Exception {
        Constructor<DataServiceImpl> declaredConstructor =
                (Constructor<DataServiceImpl>) DataServiceImpl.class.getDeclaredConstructors()[0];
        declaredConstructor.setAccessible(true);
        dataService = declaredConstructor.newInstance();
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

        Outcome outcome1 = dataService.getPossibleOutcomes().get(0);
        Outcome outcome2 = dataService.getPossibleOutcomes().get(1);
        SportEvent event1 = outcome1.getBet().getEvent();
        SportEvent event2 = outcome2.getBet().getEvent();
        event1.setResult(new Result(Arrays.asList(outcome1)));
        event2.setResult(new Result(Arrays.asList(outcome2)));
        List<Wager> wagers = Arrays.asList(
                new Wager.Builder()
                        .withEvent(event1)
                        .withPlayer(player)
                        .withOutcomeOdd(outcome1.getOutcomeOdds().get(0))
                        .withAmount(5)
                        .withCurrency(player.getCurrency())
                        .withTimestamp(System.currentTimeMillis())
                        .build(),
                new Wager.Builder()
                        .withEvent(event2)
                        .withPlayer(player)
                        .withOutcomeOdd(outcome2.getOutcomeOdds().get(0))
                        .withAmount(5)
                        .withCurrency(player.getCurrency())
                        .withTimestamp(System.currentTimeMillis())
                        .build());

        Map<Player, Double> playerPrizeMap = betService.processBets(wagers, dataService.getEvents());

        assertEquals(8.5, (double) playerPrizeMap.get(player));
        verify(playerService).increasePlayerBalance(any(Player.class), eq(8.5));
    }
}