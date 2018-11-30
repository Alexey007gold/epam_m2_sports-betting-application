package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.WagerService;
import org.springframework.stereotype.Service;

@Service
public class WagerServiceImpl implements WagerService {

    @Override
    public double calculatePrize(Wager wager) {
        return wager.getAmount() * wager.getOutcomeOdd().getValue();
    }
}
