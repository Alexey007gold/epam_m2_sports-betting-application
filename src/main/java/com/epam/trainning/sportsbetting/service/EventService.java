package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.sportevent.Result;
import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventService {

    private DataService dataService;

    public EventService(DataService dataService) {
        this.dataService = dataService;
    }

    //Sets randomized results to events
    public void playEvents() {
        for (SportEvent event : dataService.getEvents()) {
            List<Outcome> outcomes = new ArrayList<>();
            event.getBets().forEach(bet -> {
                List<Outcome> possibleOutcomes = bet.getOutcomes();
                outcomes.add(possibleOutcomes.get(new Random().nextInt(possibleOutcomes.size())));
            });
            event.setResult(new Result(outcomes));
        }
    }
}
