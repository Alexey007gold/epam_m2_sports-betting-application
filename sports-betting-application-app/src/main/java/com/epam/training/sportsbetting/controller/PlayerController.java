package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.PageDTO;
import com.epam.training.sportsbetting.PageRequest;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/player/{id}")
    public Player getPlayerInfo(@PathVariable("id") Integer id) {
        return playerService.getPlayerById(id).orElseThrow(() -> new IllegalArgumentException("Player not found"));
    }

    @GetMapping("/player/page")
    public PageDTO<Player> getPlayers(@RequestBody PageRequest pageRequest) {
        return playerService.listPlayers(pageRequest.toPageable());
    }
}
