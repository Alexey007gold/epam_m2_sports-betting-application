package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.bet.Bet;
import com.epam.trainning.sportsbetting.domain.outcome.Outcome;
import com.epam.trainning.sportsbetting.domain.outcome.OutcomeOdd;
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

import static java.util.stream.Collectors.toList;

public class DataService {

    private List<SportEvent> events;
    private List<Bet> availableBets;
    private List<Outcome> possibleOutcomes;

    public DataService() throws IOException, URISyntaxException {
        initEventData();
    }

    private void initEventData() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("eventData.json").toURI());
        String data = new String(Files.readAllBytes(path));
        ObjectMapper mapper = new ObjectMapper();

        events = mapper.readValue(data, new TypeReference<List<SportEvent>>() {});

        availableBets = events.stream()
                .map(SportEvent::getBets)
                .flatMap(Collection::stream)
                .collect(toList());
        possibleOutcomes = availableBets.stream()
                .map(Bet::getOutcomes)
                .flatMap(Collection::stream)
                .collect(toList());

        //resolve circular references
        for (SportEvent event : events) {
            for (int j = 0; j < event.getBets().size(); j++) {
                event.getBets().get(j).setEvent(event);
            }
        }
        for (Outcome possibleOutcome : possibleOutcomes) {
            for (int j = 0; j < possibleOutcome.getOutcomeOdds().size(); j++) {
                possibleOutcome.getOutcomeOdds().get(j).setOutcome(possibleOutcome);
            }
        }
        for (Bet bet : availableBets) {
            for (Outcome outcome : bet.getOutcomes()) {
                outcome.setBet(bet);
            }
        }//

        //resolve validTo null references.
        for (Bet bet : availableBets) {
            for (Outcome outcome : bet.getOutcomes()) {
                for (OutcomeOdd outcomeOdd : outcome.getOutcomeOdds()) {
                    if (outcomeOdd.getValidTo() == null) {
                        //maybe endDate? No, because odd can be valid only until the event starts
                        outcomeOdd.setValidTo(bet.getEvent().getStartDate());
                    }
                }
            }
        }
    }

    public List<SportEvent> getEvents() {
        return events;
    }

    public List<Bet> getAvailableBets() {
        return availableBets;
    }

    public List<Outcome> getPossibleOutcomes() {
        return possibleOutcomes;
    }
}
