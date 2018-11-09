package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.WagerService;

public class WagerServiceImpl implements WagerService {

    private static final WagerServiceImpl INSTANCE = new WagerServiceImpl();

    private WagerServiceImpl() {
    }

    @Override
    public double calculatePrize(Wager wager) {
        return wager.getAmount() * wager.getOutcomeOdd().getValue();
    }

    public static WagerServiceImpl getInstance() {
        return INSTANCE;
    }
}
