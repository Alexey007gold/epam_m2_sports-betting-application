package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.Result;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.service.EventService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class EventServiceImpl implements EventService {

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
