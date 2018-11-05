package com.epam.trainning.sportsbetting.service.impl;

import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;
import com.epam.trainning.sportsbetting.domain.wager.Wager;
import com.epam.trainning.sportsbetting.service.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WagerServiceImplTest {

    private WagerServiceImpl wagerService = WagerServiceImpl.getInstance();
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
    void shouldReturnCorrectResultOnCalculatePrize() {
        Outcome outcome1 = dataService.getPossibleOutcomes().get(0);
        SportEvent event1 = outcome1.getBet().getEvent();
        Wager wager = new Wager.Builder()
                .withEvent(event1)
                .withOutcomeOdd(outcome1.getOutcomeOdds().get(0))
                .withAmount(5)
                .withTimestamp(System.currentTimeMillis())
                .build();
        assertEquals(20, wagerService.calculatePrize(wager));
    }
}