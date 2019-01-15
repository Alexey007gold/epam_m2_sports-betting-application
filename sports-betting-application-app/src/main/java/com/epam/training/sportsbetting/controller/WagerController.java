package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import com.epam.training.sportsbetting.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class WagerController {

    private final WagerService wagerService;

    @Autowired
    public WagerController(WagerService wagerService) {
        this.wagerService = wagerService;
    }

    @DeleteMapping("/removeWager")
    public boolean removeWager(Authentication authentication, @RequestParam("wager_id") Integer wagerId) {
        Integer userId = ((ExtendedUserDetails) authentication.getPrincipal()).getId();

        return wagerService.removeWager(userId, wagerId);
    }

    @PostMapping("/newWager")
    public boolean newWager(Authentication authentication,
                            @RequestParam("outcome_id") Integer outcomeId,
                            @RequestParam("amount") Double amount) {
        Integer userId = ((ExtendedUserDetails) authentication.getPrincipal()).getId();

        return wagerService.newWager(userId, outcomeId, amount);
    }
}
