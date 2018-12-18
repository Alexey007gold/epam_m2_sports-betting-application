package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.PlayerService;
import com.epam.training.sportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final UserService userService;

    @Autowired
    public PlayerServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Player registerPlayer(String name, String accNum, double balance, Currency currency, LocalDate birthDate) {
        Player player = new Player.Builder()
            .withEnabled(true)
            .withName(name)
            .withAccountNumber(accNum)
            .withBalance(balance)
            .withCurrency(currency)
            .withBirthDate(birthDate)
            .build();
        userService.saveUser(player);
        return player;
    }

    @Override
    public Optional<Player> getPlayerById(Integer id) {
        return userService.getUserById(id).map(user -> (Player) user);
    }

    @Override
    public Optional<Player> getPlayerByEmail(String email) {
        return userService.getUserByEmail(email).map(u -> (Player) u);
    }

    @Override
    public Player updatePlayerByEmail(Player player) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Player updatePlayerById(Player player) {
        return (Player) userService.updateUserById(player);
    }

    @Override
    public void decreasePlayerBalance(Player player, double amount) {
        if (player.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough money");
        }
        player.setBalance(player.getBalance() - amount);
    }

    @Override
    public void decreaseBalanceByPlayerId(Integer id, double amount) {
        decreasePlayerBalance(getPlayerById(id).orElseThrow(IllegalArgumentException::new), amount);
    }

    @Override
    public void increasePlayerBalance(Player player, double amount) {
        player.setBalance(player.getBalance() + amount);
    }

    @Override
    public void increaseBalanceByPlayerId(Integer id, double amount) {
        increasePlayerBalance(getPlayerById(id).orElseThrow(IllegalArgumentException::new), amount);
    }

    @Override
    public boolean canMakeWager(Player player, double wage) {
        return player.getBalance() >= wage;
    }
}
