package com.epam.training.sportsbetting.service.impl;

import com.cookingfox.guava_preconditions.Preconditions;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.BetService;
import com.epam.training.sportsbetting.service.PlayerService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class BetServiceImpl implements BetService {

    private PlayerService playerService;

    private static final BetServiceImpl INSTANCE = new BetServiceImpl();

    private BetServiceImpl() {
    }

    @Override
    public Map<Player, Double> processBets(List<Wager> userBets, List<SportEvent> events) {
        Preconditions.checkArgument(playerService != null, "PlayerService is not set");

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

        playerToPrize.forEach((player, prize) -> playerService.increasePlayerBalance(player, prize));

        return playerToPrize;
    }

    public static BetServiceImpl getInstance() {
        return INSTANCE;
    }

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }
}
