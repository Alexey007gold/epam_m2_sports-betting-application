package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.DataService;
import com.epam.training.sportsbetting.service.OutcomeService;
import com.epam.training.sportsbetting.service.PlayerService;
import com.epam.training.sportsbetting.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WagerServiceImpl implements WagerService {

    private final DataService dataService;
    private final PlayerService playerService;
    private final OutcomeService outcomeService;

    @Autowired
    public WagerServiceImpl(DataService dataService, PlayerService playerService,
                            OutcomeService outcomeService) {
        this.dataService = dataService;
        this.playerService = playerService;
        this.outcomeService = outcomeService;
    }

    @Override
    public List<Wager> getWagersByPlayer(Player player) {
        return getWagersByPlayerEmail(player.getEmail());
    }

    @Override
    public List<Wager> getWagersByPlayerId(Integer id) {
        return dataService.getWagersByPlayerId(id);
    }

    @Override
    public List<Wager> getWagersByPlayerEmail(String email) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean newWager(Integer playerId, Integer outcomeId, Double amount) {
        Player player = playerService.getPlayerById(playerId).orElseThrow(IllegalArgumentException::new);
        Outcome outcomeById = outcomeService.getOutcomeById(outcomeId);
        dataService.addWagerByPlayerId(playerId, new Wager.Builder()
            .withAmount(amount)
            .withCurrency(player.getCurrency())
            .withTimestamp(System.currentTimeMillis())
            .withOutcomeOdd(outcomeById.getActiveOdd())
            .withEvent(outcomeById.getBet().getEvent())
            .withPlayer(player)
            .build());
        playerService.decreaseBalanceByPlayerId(playerId, amount);
        return true;
    }

    @Override
    public boolean removeWager(Integer playerId, Integer wagerId) {
        List<Wager> wagersByPlayerId = dataService.getWagersByPlayerId(playerId);
        if (wagersByPlayerId != null) {
            return wagersByPlayerId.removeIf(w -> {
                if (w.getId().equals(wagerId) && !w.isProcessed()) {
                    playerService.increaseBalanceByPlayerId(playerId, w.getAmount() * 0.75);
                    return true;
                }
                return false;
            });
        }
        throw new IllegalArgumentException("Wager not found");
    }

    @Override
    public double calculatePrize(Wager wager) {
        return wager.getAmount() * wager.getOutcomeOdd().getValue();
    }
}
