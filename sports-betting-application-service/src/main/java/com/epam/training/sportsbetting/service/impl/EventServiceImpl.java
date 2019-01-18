package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.PageDTO;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.Result;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.entity.BetEntity;
import com.epam.training.sportsbetting.entity.OutcomeEntity;
import com.epam.training.sportsbetting.entity.OutcomeOddEntity;
import com.epam.training.sportsbetting.entity.SportEventEntity;
import com.epam.training.sportsbetting.form.SportEventForm;
import com.epam.training.sportsbetting.repository.OutcomeOddRepository;
import com.epam.training.sportsbetting.repository.OutcomeRepository;
import com.epam.training.sportsbetting.repository.SportEventRepository;
import com.epam.training.sportsbetting.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventServiceImpl implements EventService {

    private final SportEventRepository eventRepository;
    private final OutcomeRepository outcomeRepository;
    private final OutcomeOddRepository outcomeOddRepository;

    private final ModelMapper mapper;

    @Autowired
    public EventServiceImpl(SportEventRepository eventRepository, OutcomeRepository outcomeRepository,
                            OutcomeOddRepository outcomeOddRepository, ModelMapper mapper) {
        this.eventRepository = eventRepository;
        this.outcomeRepository = outcomeRepository;
        this.outcomeOddRepository = outcomeOddRepository;
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

    @Override
    public List<SportEvent> addEvents(List<SportEventForm> events) {
        List<SportEventEntity> entities = events.stream()
                .map(e -> {
                    SportEventEntity entity = mapper.map(e, SportEventEntity.class);
                    entity.getBets().forEach(b -> b.setEvent(entity));
                    return entity;
                }).collect(Collectors.toList());

        Map<BetEntity, List<OutcomeEntity>> betToOutcomesMap = new HashMap<>();
        Map<OutcomeEntity, List<OutcomeOddEntity>> outcomeToOddsMap = new HashMap<>();
        entities.forEach(e -> {
            e.getBets().forEach(b -> {
                if (b.getOutcomes() != null) {
                    betToOutcomesMap.put(b, b.getOutcomes());
                    b.getOutcomes().forEach(o -> {
                        if (o.getOutcomeOdds() != null) {
                            outcomeToOddsMap.put(o, o.getOutcomeOdds());
                            o.setOutcomeOdds(null);
                        }
                    });
                    b.setOutcomes(null);
                }
            });
        });
        eventRepository.saveAll(entities);

        betToOutcomesMap.forEach((bet, outcomes) -> {
            outcomes.forEach(o -> o.setBetId(bet.getId()));
            outcomeRepository.saveAll(outcomes);
            bet.setOutcomes(outcomes);
        });

        outcomeToOddsMap.forEach((o, odds) -> {
            odds.forEach(odd -> odd.setOutcomeId(o.getId()));
            outcomeOddRepository.saveAll(odds);
            o.setOutcomeOdds(odds);
        });

        return entities.stream()
                .map(e -> mapper.map(e, SportEvent.class))
                .collect(Collectors.toList());
    }

    @Override
    public PageDTO<SportEvent> getEvents(Pageable pageRequest) {
        return PageDTO.of(eventRepository.findAll(pageRequest).map(e -> mapper.map(e, SportEvent.class)));
    }
}
