package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.BetService;
import com.epam.training.sportsbetting.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class BetServiceImpl implements BetService {

    private final PlayerService playerService;

    @Autowired
    public BetServiceImpl(PlayerService playerService) {
        this.playerService = playerService;
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
}
