package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.sportevent.Result;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.service.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceImplTest {

    private EventServiceImpl eventService = EventServiceImpl.getInstance();

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
    void shouldSetRandomizedResultsToEventsOnPlayEvents() {
        List<SportEvent> events = dataService.getEvents();
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
}