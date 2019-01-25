package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.entity.OutcomeOddEntity;
import com.epam.training.sportsbetting.repository.OutcomeRepository;
import com.epam.training.sportsbetting.service.OutcomeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutcomeServiceImpl implements OutcomeService {

    private final OutcomeRepository outcomeRepository;

    private final ModelMapper mapper;

    @Autowired
    public OutcomeServiceImpl(OutcomeRepository outcomeRepository, ModelMapper mapper) {
        this.outcomeRepository = outcomeRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Outcome getOutcomeById(Integer id) {
        return mapper.map(outcomeRepository.findById(id).orElseThrow(), Outcome.class);
    }

    public OutcomeOdd getActiveOdd(List<OutcomeOdd> outcomeOdds) {
        if (outcomeOdds == null) return null;

        LocalDateTime now = LocalDateTime.now();
        for (OutcomeOdd outcomeOdd : outcomeOdds) {
            LocalDateTime validFrom = outcomeOdd.getValidFrom();
            LocalDateTime validTo = outcomeOdd.getValidTo();
            if (isDateTimeBetween(now, validFrom, validTo)) {
                return outcomeOdd;
            }
        }
        return null;
    }

    public OutcomeOddEntity getActiveOddEntity(List<OutcomeOddEntity> outcomeOdds, LocalDateTime eventStartDateTime) {
        if (outcomeOdds == null) return null;

        LocalDateTime now = LocalDateTime.now();
        for (OutcomeOddEntity outcomeOdd : outcomeOdds) {
            LocalDateTime validFrom = outcomeOdd.getValidFrom();
            LocalDateTime validTo = outcomeOdd.getValidTo() == null ? eventStartDateTime : outcomeOdd.getValidTo();
            if (isDateTimeBetween(now, validFrom, validTo)) {
                return outcomeOdd;
            }
        }
        return null;
    }

    private boolean isDateTimeBetween(LocalDateTime toCheck, LocalDateTime from, LocalDateTime to) {
        return (from.isBefore(toCheck) || from.isEqual(toCheck)) &&
            (to.isAfter(toCheck) || from.isEqual(toCheck));

    }
}
