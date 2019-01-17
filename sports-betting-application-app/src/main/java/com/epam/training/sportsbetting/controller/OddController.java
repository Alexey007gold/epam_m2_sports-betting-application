package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.form.AddOutcomeOddForm;
import com.epam.training.sportsbetting.service.OutcomeOddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OddController {

    private final OutcomeOddService outcomeOddService;

    @Autowired
    public OddController(OutcomeOddService outcomeOddService) {
        this.outcomeOddService = outcomeOddService;
    }

    @PostMapping("/odd/add")
    public List<OutcomeOdd> addOdds(@RequestBody List<AddOutcomeOddForm> events) {
        validateForm(events);
        return outcomeOddService.addOutcomeOdds(events);
    }

    private void validateForm(List<AddOutcomeOddForm> outcomeOddForms) {
        outcomeOddForms.forEach(odd -> {
            if (odd.getValue() <= 0)
                throw new IllegalArgumentException("Odd value should be positive");
            if (odd.getValidFrom() == null)
                throw new IllegalArgumentException("ValidFrom should not be null");
        });
    }
}
