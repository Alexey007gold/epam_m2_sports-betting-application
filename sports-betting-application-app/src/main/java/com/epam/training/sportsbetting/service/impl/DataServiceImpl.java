package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.service.DataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class DataServiceImpl implements DataService {

    private List<SportEvent> events;
    private List<Bet> availableBets;
    private List<Outcome> possibleOutcomes;

    @PostConstruct
    private void initEventData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        InputStream resource = getClass().getClassLoader().getResourceAsStream("eventData.json");
        events = mapper.readValue(resource, new TypeReference<List<SportEvent>>() {
        });

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

    @Override
    public List<SportEvent> getEvents() {
        return events;
    }

    @Override
    public List<Bet> getAvailableBets() {
        return availableBets;
    }

    @Override
    public List<Outcome> getPossibleOutcomes() {
        return possibleOutcomes;
    }
}
