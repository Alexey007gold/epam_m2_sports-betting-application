package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.sportevent.SportEvent;

import java.util.List;

public interface EventService {
    /**
     *
     //Sets randomized results to events
     * @param eventsToPlay
     */
    void playEvents(List<SportEvent> eventsToPlay);
}
