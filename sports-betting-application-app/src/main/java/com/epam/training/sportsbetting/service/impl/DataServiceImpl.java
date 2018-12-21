package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.bet.Bet;
import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.outcome.OutcomeOdd;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.DataService;
import com.epam.training.sportsbetting.service.PlayerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class DataServiceImpl implements DataService, InitializingBean {

    private List<SportEvent> events;
    private List<Bet> availableBets;
    private List<Outcome> possibleOutcomes;

    //temporary stab with data for home page
    private Map<Integer, List<Wager>> wagerMap;
    private int lastWagerId;

    //temporary stab for initializing data for home page
    private final PlayerService playerService;

    @Autowired
    public DataServiceImpl(PlayerService playerService) {
        this.playerService = playerService;
    }

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




        //temporary stab for initializing data for home page
        Optional<Player> player = playerService.getPlayerById(1);

        wagerMap = new HashMap<>();
        List<Wager> wagers = new ArrayList<>();
        wagers.add(new Wager.Builder()
            .withId(lastWagerId++)
            .withEvent(events.get(0))
            .withPlayer(player.get())
            .withOutcomeOdd(possibleOutcomes.get(0).getActiveOdd())
            .withAmount(10000)
            .withCurrency(player.get().getCurrency())
            .withTimestamp(System.currentTimeMillis())
            .build());
        wagers.add(new Wager.Builder()
            .withId(lastWagerId++)
            .withEvent(events.get(0))
            .withPlayer(player.get())
            .withOutcomeOdd(possibleOutcomes.get(1).getActiveOdd())
            .withAmount(8000)
            .withCurrency(player.get().getCurrency())
            .withTimestamp(System.currentTimeMillis())
            .withWinner(true)
            .withProcessed(true)
            .build());
        wagers.add(new Wager.Builder()
            .withId(lastWagerId++)
            .withEvent(events.get(0))
            .withPlayer(player.get())
            .withOutcomeOdd(possibleOutcomes.get(2).getActiveOdd())
            .withAmount(5000)
            .withCurrency(player.get().getCurrency())
            .withTimestamp(System.currentTimeMillis())
            .withWinner(false)
            .withProcessed(true)
            .build());
        wagers.add(new Wager.Builder()
            .withId(lastWagerId++)
            .withEvent(events.get(1))
            .withPlayer(player.get())
            .withOutcomeOdd(possibleOutcomes.get(1).getActiveOdd())
            .withAmount(12000)
            .withCurrency(player.get().getCurrency())
            .withTimestamp(System.currentTimeMillis())
            .build());
        wagerMap.put(player.get().getId(), wagers);
        //
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

    @Override
    public List<Wager> getWagersByPlayerId(Integer id) {
        return wagerMap.get(id);
    }

    @Override
    public List<Wager> addWagerByPlayerId(Integer id, Wager wager) {
        wager.setId(lastWagerId++);
        return wagerMap.merge(id, new ArrayList<>(Collections.singletonList(wager)), (wagers, wagers2) -> {
            wagers.add(wagers2.get(0));
            return wagers;
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //PostConstruct alternative for java 9+
        initEventData();
    }
}
