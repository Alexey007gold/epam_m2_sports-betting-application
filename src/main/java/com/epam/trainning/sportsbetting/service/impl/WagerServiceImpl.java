package com.epam.trainning.sportsbetting.service.impl;

import com.epam.trainning.sportsbetting.domain.wager.Wager;
import com.epam.trainning.sportsbetting.service.WagerService;

public class WagerServiceImpl implements WagerService {

    @Override
    public double calculatePrize(Wager wager) {
        return wager.getAmount() * wager.getOutcomeOdd().getValue();
    }
}
