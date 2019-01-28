package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.entity.OutcomeEntity;
import com.epam.training.sportsbetting.entity.ResultEntity;
import com.epam.training.sportsbetting.entity.SportEventEntity;
import com.epam.training.sportsbetting.form.AddResultForm;
import com.epam.training.sportsbetting.repository.OutcomeRepository;
import com.epam.training.sportsbetting.repository.SportEventRepository;
import com.epam.training.sportsbetting.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ResultServiceImpl implements ResultService {

    private SportEventRepository eventRepository;

    private OutcomeRepository outcomeRepository;

    @Autowired
    public ResultServiceImpl(SportEventRepository eventRepository, OutcomeRepository outcomeRepository) {
        this.eventRepository = eventRepository;
        this.outcomeRepository = outcomeRepository;
    }

    @Override
    @Transactional
    public Set<Integer> addResults(List<AddResultForm> addResultFormList) {
        Map<Integer, SportEventEntity> eventIdToEventEntityMap = new HashMap<>();
        addResultFormList.forEach(f -> {
            Integer eventId = f.getEventId();
            Integer outcomeId = f.getOutcomeId();
            SportEventEntity eventEntity = eventIdToEventEntityMap.get(eventId);
            ResultEntity result;
            if (eventEntity == null) {
                eventEntity = eventRepository.findById(eventId)
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Event with id %d not found", eventId)));
                eventIdToEventEntityMap.put(eventId, eventEntity);

                if (eventEntity.getResult() != null) {
                    throw new IllegalStateException(String.format("Event with id %d already has results", eventId));
                }
                eventEntity.setResult(new ResultEntity());
                eventEntity.getResult().setOutcomes(new ArrayList<>());
                eventRepository.save(eventEntity);
            }
            result = eventEntity.getResult();

            OutcomeEntity outcomeEntity = outcomeRepository.findByIdAndBetEventId(outcomeId, eventId);
            if (outcomeEntity != null) {
                outcomeEntity.setResultId(result.getId());
                result.getOutcomes().add(outcomeEntity);
                outcomeRepository.save(outcomeEntity);
            } else {
                throw new IllegalArgumentException(String.format("Outcome with id %d for event with id %d not found", outcomeId, eventId));
            }
        });
        return eventIdToEventEntityMap.keySet();
    }
}
