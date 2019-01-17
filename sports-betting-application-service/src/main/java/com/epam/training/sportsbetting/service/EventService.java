package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.form.SportEventForm;

import java.util.List;

public interface EventService {
    /**
     *
     //Sets randomized results to events
     * @param eventsToPlay
     */
    void playEvents(List<SportEvent> eventsToPlay);

    List<SportEvent> getAllEvents();

    List<SportEvent> getFutureEvents();

    List<SportEvent> addEvents(List<SportEventForm> events);
}
