package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.wager.Wager;

public interface WagerService {
    double calculatePrize(Wager wager);
}
