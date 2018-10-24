package com.epam.trainning.sportsbetting.service.impl;

import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.sportevent.Result;
import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;
import com.epam.trainning.sportsbetting.service.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventServiceImpl implements EventService {

    public EventServiceImpl() {
    }

    @Override
    public void playEvents(List<SportEvent> eventsToPlay) {
        for (SportEvent event : eventsToPlay) {
            List<Outcome> outcomes = new ArrayList<>();
            event.getBets().forEach(bet -> {
                List<Outcome> possibleOutcomes = bet.getOutcomes();
                outcomes.add(possibleOutcomes.get(new Random().nextInt(possibleOutcomes.size())));
            });
            event.setResult(new Result(outcomes));
        }
    }
}