package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.PageDTO;
import com.epam.training.sportsbetting.PageRequest;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.epam.training.sportsbetting.Role.ROLE_ADMIN;

@RestController
public class PlayerController extends AbstractController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/api/player/{id}")
    public Player getPlayerInfo(Authentication authentication, @PathVariable("id") Integer id) {
        checkForRole(authentication, ROLE_ADMIN);
        return playerService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player not found"));
    }

    @GetMapping("/api/player/page")
    public PageDTO<Player> getPlayers(Authentication authentication, @RequestBody PageRequest pageRequest) {
        checkForRole(authentication, ROLE_ADMIN);
        return playerService.listPlayers(pageRequest.toPageable());
    }
}
