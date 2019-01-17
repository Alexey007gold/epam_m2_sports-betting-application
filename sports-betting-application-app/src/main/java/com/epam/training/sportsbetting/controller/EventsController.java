package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.form.SportEventForm;
import com.epam.training.sportsbetting.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EventsController {

    private static final String EVENTS_KEY = "events";

    private EventService eventService;

    @Autowired
    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event")
    public ModelAndView events() {
        ModelAndView mav = new ModelAndView("events");
        mav.addObject(EVENTS_KEY, eventService.getFutureEvents());
        return mav;
    }

    @PostMapping("/event/add")
    @ResponseBody
    public List<SportEvent> addEvents(@RequestBody List<SportEventForm> events) {
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
