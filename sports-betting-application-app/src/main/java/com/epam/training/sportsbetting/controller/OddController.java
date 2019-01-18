package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.form.AddOutcomeOddForm;
import com.epam.training.sportsbetting.service.OutcomeOddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.epam.training.sportsbetting.Role.ROLE_ADMIN;

@RestController
public class OddController extends AbstractController {

    private final OutcomeOddService outcomeOddService;

    @Autowired
    public OddController(OutcomeOddService outcomeOddService) {
        this.outcomeOddService = outcomeOddService;
    }

    @PostMapping("/api/odd/add")
    public List<OutcomeOdd> addOdds(Authentication authentication, @RequestBody List<AddOutcomeOddForm> events) {
        checkForRole(authentication, ROLE_ADMIN);
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
