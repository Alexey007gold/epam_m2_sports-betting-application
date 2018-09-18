package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.bet.Bet;
import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.sportevent.SportEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class DataService {

    private List<SportEvent> events;
    private List<Bet> availableBets;
    private List<Outcome> possibleOutcomes;
    private Map<SportEvent, List<Bet>> eventToAvailableBetsMap;

    public DataService() throws IOException, URISyntaxException {
        initEventData();
    }

    private void initEventData() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("eventData.json").toURI());
        String data = new String(Files.readAllBytes(path));
        ObjectMapper mapper = new ObjectMapper();
        availableBets = mapper.readValue(data, new TypeReference<List<Bet>>() {});

        events = availableBets.stream()
                .map(Bet::getEvent)
                .distinct()
                .collect(Collectors.toList());
        possibleOutcomes = availableBets.stream()
                .map(Bet::getOutcomes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        eventToAvailableBetsMap = availableBets.stream()
                .collect(groupingBy(Bet::getEvent));
    }

    public List<SportEvent> getEvents() {
        return events;
    }

    public void setEvents(List<SportEvent> events) {
        this.events = events;
    }

    public List<Bet> getAvailableBets() {
        return availableBets;
    }

    public void setAvailableBets(List<Bet> availableBets) {
        this.availableBets = availableBets;
    }

    public List<Outcome> getPossibleOutcomes() {
        return possibleOutcomes;
    }

    public void setPossibleOutcomes(List<Outcome> possibleOutcomes) {
        this.possibleOutcomes = possibleOutcomes;
    }

    public Map<SportEvent, List<Bet>> getEventToAvailableBetsMap() {
        return eventToAvailableBetsMap;
    }

    public void setEventToAvailableBetsMap(Map<SportEvent, List<Bet>> eventToAvailableBetsMap) {
        this.eventToAvailableBetsMap = eventToAvailableBetsMap;
    }
}
