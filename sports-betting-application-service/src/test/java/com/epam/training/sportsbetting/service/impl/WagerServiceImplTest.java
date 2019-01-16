package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.wager.Wager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class WagerServiceImplTest {

    @InjectMocks
    private WagerServiceImpl wagerService;

    @Test
    void shouldReturnCorrectResultOnCalculatePrize() {
        Outcome outcome1 = getOutcome();
        Wager wager = new Wager.Builder()
            .withOutcomeOdd(outcome1.getOutcomeOdds().get(0))
            .withAmount(5)
            .build();
        assertEquals(20, wagerService.calculatePrize(wager));
    }

    private Outcome getOutcome() {
        Outcome outcome = new Outcome("Southampton");
        OutcomeOdd odd1 = new OutcomeOdd(outcome, 4,
            LocalDateTime.parse("2018-09-23T19:00"), LocalDateTime.parse("2018-09-30T18:59"));
        outcome.setOutcomeOdds(Collections.singletonList(odd1));
        return outcome;
    }

    @Test
    void shouldThrowAnExceptionOnCalculatePrize() {
        Wager wager = new Wager.Builder()
            .withAmount(5)
            .build();
        assertThrows(NullPointerException.class, () -> wagerService.calculatePrize(wager));
    }
}