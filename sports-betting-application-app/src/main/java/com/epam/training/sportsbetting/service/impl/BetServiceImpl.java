package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.repository.BetRepository;
import com.epam.training.sportsbetting.service.BetService;
import com.epam.training.sportsbetting.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class BetServiceImpl implements BetService {

    private final PlayerService playerService;
    private final BetRepository betRepository;

    private final ModelMapper mapper;

    @Autowired
    public BetServiceImpl(PlayerService playerService, BetRepository betRepository, ModelMapper mapper) {
        this.playerService = playerService;
        this.betRepository = betRepository;
        this.mapper = mapper;
    }

    @Override
    public Map<Player, Double> processBets(List<Wager> userBets, List<SportEvent> events) {
        if (userBets.isEmpty()) return Collections.emptyMap();

        Set<Outcome> actualOutcomes = events.stream()
                .map(e -> e.getResult() != null ? e.getResult().getOutcomes() : Collections.<Outcome>emptyList())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Map<Player, Double> playerToPrize = userBets.stream()
                .map(Wager::getPlayer)
                .distinct()
                .collect(toMap(Function.identity(), p -> .0));

        for (Wager wager : userBets) {
            Outcome outcome = wager.getOutcomeOdd().getOutcome();

            wager.setProcessed(true);
            if (actualOutcomes.contains(outcome)) {
                wager.setWinner(true);

                double prize = wager.getAmount() * wager.getOutcomeOdd().getValue();
                playerToPrize.merge(wager.getPlayer(), prize, Double::sum);
            }
        }

        playerToPrize.forEach(playerService::increasePlayerBalance);

        return playerToPrize;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bet> getBetsByEventId(Integer eventId) {
        return betRepository.findByEventId(eventId).stream()
            .map(e -> mapper.map(e, Bet.class))
            .collect(Collectors.toList());
    }
}
