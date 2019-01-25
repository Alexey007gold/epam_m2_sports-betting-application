package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.Result;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.repository.SportEventRepository;
import com.epam.training.sportsbetting.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventServiceImpl implements EventService {

    private final SportEventRepository eventRepository;

    private final ModelMapper mapper;

    @Autowired
    public EventServiceImpl(SportEventRepository eventRepository, ModelMapper mapper) {
        this.eventRepository = eventRepository;
        this.mapper = mapper;
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

    @Override
    @Transactional(readOnly = true)
    public List<SportEvent> getAllEvents() {
        return StreamSupport.stream(eventRepository.findAll().spliterator(), false)
                .map(e -> mapper.map(e, SportEvent.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SportEvent> getFutureEvents() {
        return eventRepository.getFutureEvents().stream()
                .map(e -> mapper.map(e, SportEvent.class))
                .collect(Collectors.toList());
    }
}
