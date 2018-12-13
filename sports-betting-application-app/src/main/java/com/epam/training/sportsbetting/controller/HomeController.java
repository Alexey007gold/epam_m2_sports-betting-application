package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.domain.outcome.Outcome;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.service.DataService;
import com.epam.training.sportsbetting.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.*;

@Controller
public class HomeController {

    private static final String USER_KEY = "user";
    private static final String WAGERS_KEY = "wagers";
    private static final String CURRENCY_OPTIONS = "currencyOptions";

    private PlayerService playerService;
    private DataService dataService;


    //temporary stab with data for home page
    private Map<Integer, Set<Wager>> wagerMap;

    //temporary stab for initializing data for home page
    @PostConstruct
    public void init() {
        Optional<Player> player = playerService.getPlayerById(1);
        List<SportEvent> events = dataService.getEvents();
        List<Outcome> possibleOutcomes = dataService.getPossibleOutcomes();

        wagerMap = new HashMap<>();
        Set<Wager> wagers = new HashSet<>();
        wagers.add(new Wager.Builder()
            .withId(1)
            .withEvent(events.get(0))
            .withPlayer(player.get())
            .withOutcomeOdd(possibleOutcomes.get(0).getActiveOdd())
            .withAmount(10000)
            .withCurrency(player.get().getCurrency())
            .withTimestamp(System.currentTimeMillis())
            .build());
        wagers.add(new Wager.Builder()
            .withId(2)
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
            .withId(3)
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
            .withId(4)
            .withEvent(events.get(1))
            .withPlayer(player.get())
            .withOutcomeOdd(possibleOutcomes.get(1).getActiveOdd())
            .withAmount(12000)
            .withCurrency(player.get().getCurrency())
            .withTimestamp(System.currentTimeMillis())
            .build());
        wagerMap.put(player.get().getId(), wagers);
    }

    @Autowired
    public HomeController(PlayerService playerService, DataService dataService) {
        this.playerService = playerService;
        this.dataService = dataService;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        Optional<Player> player = playerService.getPlayerById(1);

        return getModelAndView(player
            .orElseThrow(() -> new IllegalStateException("Player was not found")));
    }

    @PostMapping("/updateAccount")
    public ModelAndView updateAccount(@ModelAttribute @Valid Player player) {
        Optional<Player> result = playerService.updatePlayerById(player);

        return getModelAndView(result
            .orElseThrow(() -> new IllegalStateException("Player does not exist")));
    }

    @DeleteMapping("/removeWager")
    @ResponseBody
    public boolean removeWager(@RequestParam("player_id") Integer playerId, @RequestParam("wager_id") Integer wagerId) {
        return wagerMap.get(playerId).removeIf(w -> w.getId().equals(wagerId));
    }

    private ModelAndView getModelAndView(Player player) {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject(USER_KEY, player);
        mav.addObject(WAGERS_KEY, wagerMap.get(player.getId()));
        mav.addObject(CURRENCY_OPTIONS, Currency.values());
        return mav;
    }
}
