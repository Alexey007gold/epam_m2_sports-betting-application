package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.ExtendedUserDetails;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.PlayerService;
import com.epam.training.sportsbetting.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class HomeController {

    private static final String USER_KEY = "user";
    private static final String WAGERS_KEY = "wagers";
    private static final String CURRENCY_OPTIONS = "currencyOptions";

    private PlayerService playerService;
    private WagerService wagerService;

    @Autowired
    public HomeController(PlayerService playerService, WagerService wagerService) {
        this.playerService = playerService;
        this.wagerService = wagerService;
    }

    @GetMapping("/home")
    public ModelAndView home(Authentication authentication) {
        Integer userId = ((ExtendedUserDetails) authentication.getPrincipal()).getId();
        Optional<Player> player = playerService.getPlayerById(userId);

        return getModelAndView(player
            .orElseThrow(() -> new IllegalStateException("Player was not found")));
    }

    @PostMapping("/updateAccount")
    public ModelAndView updateAccount(Authentication authentication, @ModelAttribute @Valid Player player) {
        Integer userId = ((ExtendedUserDetails) authentication.getPrincipal()).getId();
        player.setId(userId);
        Player result = playerService.updatePlayerById(player);

        return getModelAndView(result);
    }

    private ModelAndView getModelAndView(Player player) {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject(USER_KEY, player);
        mav.addObject(WAGERS_KEY, wagerService.getWagersByPlayerId(player.getId()));
        mav.addObject(CURRENCY_OPTIONS, Currency.values());
        return mav;
    }
}
