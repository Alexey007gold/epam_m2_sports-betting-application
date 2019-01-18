package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.repository.BetRepository;
import com.epam.training.sportsbetting.service.BetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BetServiceImpl implements BetService {

    private final BetRepository betRepository;

    private final ModelMapper mapper;

    @Autowired
    public BetServiceImpl(BetRepository betRepository, ModelMapper mapper) {
        this.betRepository = betRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bet> getBetsByEventId(Integer eventId) {
        return betRepository.findByEventId(eventId).stream()
            .map(e -> mapper.map(e, Bet.class))
            .collect(Collectors.toList());
    }
}
