package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventsController {

    private static final String EVENTS_KEY = "events";

    private EventService eventService;

    @Autowired
    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public ModelAndView events() {
        ModelAndView mav = new ModelAndView("events");
        mav.addObject(EVENTS_KEY, eventService.getAllEvents());
        return mav;
    }
}
