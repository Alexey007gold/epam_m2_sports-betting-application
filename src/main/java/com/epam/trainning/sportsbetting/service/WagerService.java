package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.wager.Wager;

public class WagerService {

    public double calculatePrize(Wager wager) {
        return wager.getAmount() * wager.getOutcomeOdd().getValue();
    }
}
