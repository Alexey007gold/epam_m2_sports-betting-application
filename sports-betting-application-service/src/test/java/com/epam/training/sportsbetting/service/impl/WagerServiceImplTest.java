package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.entity.OutcomeEntity;
import com.epam.training.sportsbetting.entity.OutcomeOddEntity;
import com.epam.training.sportsbetting.entity.WagerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class WagerServiceImplTest {

    @InjectMocks
    private WagerServiceImpl wagerService;

    @Test
    void shouldReturnCorrectResultOnCalculatePrize() {
        OutcomeEntity outcome1 = getOutcome();
        WagerEntity wager = WagerEntity.builder()
                .outcomeOdd(outcome1.getOutcomeOdds().get(0))
                .amount(5)
                .build();
        assertEquals(20, wagerService.calculatePrize(wager));
    }

    private OutcomeEntity getOutcome() {
        OutcomeEntity outcome = new OutcomeEntity();
        OutcomeOddEntity odd1 = new OutcomeOddEntity();
        odd1.setValue(4);
        outcome.setOutcomeOdds(Collections.singletonList(odd1));
        return outcome;
    }

    @Test
    void shouldThrowAnExceptionOnCalculatePrize() {
        WagerEntity wager = WagerEntity.builder()
                .amount(5)
                .build();
        assertThrows(NullPointerException.class, () -> wagerService.calculatePrize(wager));
    }
}