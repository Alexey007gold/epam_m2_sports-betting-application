package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;

import java.util.List;

public interface EventService {
    /**
     *
     //Sets randomized results to events
     * @param eventsToPlay
     */
    void playEvents(List<SportEvent> eventsToPlay);
}
