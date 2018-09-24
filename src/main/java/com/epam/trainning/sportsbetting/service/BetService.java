package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.user.Player;
import com.epam.trainning.sportsbetting.domain.wager.Wager;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BetService {

    private DataService dataService;
    private PlayerService playerService;

    public BetService(DataService dataService, PlayerService playerService) {
        this.dataService = dataService;
        this.playerService = playerService;
    }

    public void processBets(Map<Outcome, Wager> userBets) {
        if (userBets.isEmpty()) return;
        Player player = null;
        double prize = 0;

        Set<Outcome> realOutcomes = dataService.getEvents().stream()
                .map(e -> e.getResult() != null ? e.getResult().getOutcomes() : Collections.<Outcome>emptyList())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        for (Map.Entry<Outcome, Wager> entry : userBets.entrySet()) {
            Outcome outcome = entry.getKey();
            Wager wager = entry.getValue();

            wager.setProcessed(true);
            if (realOutcomes.contains(outcome)) {
                if (player == null) {
                    player = wager.getPlayer();
                }
                wager.setWin(true);

                prize += wager.getAmount() * wager.getOutcomeOdd().getValue();
            }
        }

        if (player != null) {
            playerService.increasePlayerBalance(player, prize);
        }
    }
}
