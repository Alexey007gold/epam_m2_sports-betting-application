package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.BetService;
import com.epam.training.sportsbetting.service.EventService;
import com.epam.training.sportsbetting.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class EventsController {

    private static final String EVENTS_KEY = "events";
    private static final String BETS_KEY = "bets";
    private static final String CURRENCY_KEY = "currency";

    private PlayerService playerService;
    private EventService eventService;
    private BetService betService;

    @Autowired
    public EventsController(PlayerService playerService, EventService eventService,
                            BetService betService) {
        this.playerService = playerService;
        this.eventService = eventService;
        this.betService = betService;
    }

    @GetMapping("/events")
    public ModelAndView events() {
        ModelAndView mav = new ModelAndView("events");
        mav.addObject(EVENTS_KEY, eventService.getAllEvents());
        return mav;
    }

    @GetMapping("/bets")
    public ModelAndView bets(Authentication authentication, @RequestParam("event_id") Integer eventId) {
        Integer userId = ((ExtendedUserDetails) authentication.getPrincipal()).getId();
        Optional<Player> player = playerService.getPlayerById(userId);

        ModelAndView mav = new ModelAndView("bets");
        mav.addObject(BETS_KEY, betService.getBetsByEventId(eventId));
        mav.addObject(CURRENCY_KEY, player.orElseThrow(IllegalStateException::new).getCurrency());
        return mav;
    }
}
