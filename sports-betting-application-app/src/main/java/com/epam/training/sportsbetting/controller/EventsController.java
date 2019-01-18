package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.PageDTO;
import com.epam.training.sportsbetting.PageRequest;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.form.SportEventForm;
import com.epam.training.sportsbetting.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.epam.training.sportsbetting.Role.ROLE_ADMIN;
import static com.epam.training.sportsbetting.Role.ROLE_PLAYER;
import static java.util.stream.Collectors.toList;

@Controller
public class EventsController extends AbstractController {

    private static final String EVENTS_KEY = "events";

    private EventService eventService;

    @Autowired
    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event")
    public ModelAndView events(Authentication authentication) {
        checkForRole(authentication, ROLE_PLAYER);
        ModelAndView mav = new ModelAndView("events");
        List<SportEvent> events = eventService.getFutureEvents().stream()
                .filter(e -> e.getResult() == null)
                .collect(toList());
        mav.addObject(EVENTS_KEY, events);
        return mav;
    }

    @GetMapping("/api/event/page")
    @ResponseBody
    public PageDTO<SportEvent> eventsByPage(Authentication authentication, @RequestBody PageRequest pageRequest) {
        checkForRole(authentication, ROLE_ADMIN);
        return eventService.getEvents(pageRequest.toPageable());
    }

    @PostMapping("/api/event/add")
    @ResponseBody
    public List<SportEvent> addEvents(Authentication authentication, @RequestBody List<SportEventForm> events) {
        checkForRole(authentication, ROLE_ADMIN);
        validateEventsForm(events);
        return eventService.addEvents(events);
    }

    private void validateEventsForm(List<SportEventForm> events) {
        events.forEach(e -> {
            if (e.getBets() == null || e.getBets().isEmpty())
                throw new IllegalArgumentException("Event should contain at least one bet");
            e.getBets().forEach(b -> {
                if (b.getOutcomes() == null || b.getOutcomes().isEmpty())
                    throw new IllegalArgumentException("Bet should contain at least one outcome");
            });
        });
    }
}
