package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.entity.OutcomeOddEntity;
import com.epam.training.sportsbetting.form.AddOutcomeOddForm;
import com.epam.training.sportsbetting.repository.OutcomeOddRepository;
import com.epam.training.sportsbetting.service.OutcomeOddService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OutcomeOddServiceImpl implements OutcomeOddService {

    private final OutcomeOddRepository outcomeOddRepository;

    private final ModelMapper mapper;

    @Autowired
    public OutcomeOddServiceImpl(OutcomeOddRepository outcomeOddRepository, ModelMapper mapper) {
        this.outcomeOddRepository = outcomeOddRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public List<OutcomeOdd> addOutcomeOdds(List<AddOutcomeOddForm> outcomeOddFormList) {
        List<OutcomeOddEntity> entities = outcomeOddFormList.stream()
                .map(odd -> mapper.map(odd, OutcomeOddEntity.class))
                .collect(Collectors.toList());
        return StreamSupport.stream(outcomeOddRepository.saveAll(entities).spliterator(), true)
                .map(e -> mapper.map(e, OutcomeOdd.class))
                .collect(Collectors.toList());
    }
}
