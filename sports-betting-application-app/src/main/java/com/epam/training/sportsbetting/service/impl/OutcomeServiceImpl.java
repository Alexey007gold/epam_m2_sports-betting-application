package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.service.DataService;
import com.epam.training.sportsbetting.service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutcomeServiceImpl implements OutcomeService {

    private final DataService dataService;

    @Autowired
    public OutcomeServiceImpl(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public Outcome getOutcomeById(Integer id) {
        return dataService.getPossibleOutcomes().stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
    }
}
