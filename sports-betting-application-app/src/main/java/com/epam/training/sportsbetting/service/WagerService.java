package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;

import java.util.Set;

public interface WagerService {

    Set<Wager> getWagersByPlayer(Player player);

    Set<Wager> getWagersByPlayerEmail(String email);

    double calculatePrize(Wager wager);
}
