package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.BetService;
import com.epam.training.sportsbetting.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Created by Oleksii_Kovetskyi on 12/21/2018.
 */
@Controller
public class BetController {

    private static final String BETS_KEY = "bets";
    private static final String CURRENCY_KEY = "currency";

    private BetService betService;
    private PlayerService playerService;

    @Autowired
    public BetController(BetService betService, PlayerService playerService) {
        this.betService = betService;
        this.playerService = playerService;
    }

    @GetMapping("/bets/{event_id}")
    public ModelAndView bets(Authentication authentication, @PathVariable("event_id") Integer eventId) {
        Integer userId = ((ExtendedUserDetails) authentication.getPrincipal()).getId();
        Optional<Player> player = playerService.getPlayerById(userId);

        ModelAndView mav = new ModelAndView("bets");
        mav.addObject(BETS_KEY, betService.getBetsByEventId(eventId));
        mav.addObject(CURRENCY_KEY, player.orElseThrow(IllegalStateException::new).getCurrency());
        return mav;
    }
}
