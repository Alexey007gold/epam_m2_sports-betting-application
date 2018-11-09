package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.wager.Wager;

public interface WagerService {
    double calculatePrize(Wager wager);
}
