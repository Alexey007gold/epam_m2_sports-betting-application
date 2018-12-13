package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.WagerService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WagerServiceImpl implements WagerService {

    @Override
    public Set<Wager> getWagersByPlayer(Player player) {
        return getWagersByPlayerEmail(player.getEmail());
    }

    @Override
    public Set<Wager> getWagersByPlayerEmail(String email) {
        return null;
    }

    @Override
    public double calculatePrize(Wager wager) {
        return wager.getAmount() * wager.getOutcomeOdd().getValue();
    }
}
