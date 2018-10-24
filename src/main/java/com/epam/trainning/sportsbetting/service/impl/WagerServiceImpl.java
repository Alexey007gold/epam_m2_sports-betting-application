package com.epam.trainning.sportsbetting.service.impl;

import com.epam.trainning.sportsbetting.domain.wager.Wager;
import com.epam.trainning.sportsbetting.service.WagerService;

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
