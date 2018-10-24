package com.epam.trainning.sportsbetting.service.impl;

import com.epam.trainning.sportsbetting.domain.user.Currency;
import com.epam.trainning.sportsbetting.domain.user.Player;
import com.epam.trainning.sportsbetting.service.PlayerService;

import java.time.LocalDate;

public class PlayerServiceImpl implements PlayerService {

    public PlayerServiceImpl() {
    }

    @Override
    public Player registerPlayer(String name, String accNum, double balance, Currency currency, LocalDate birthDate) {
        return new Player.Builder()
                .withEnabled(true)
                .withName(name)
                .withAccountNumber(accNum)
                .withBalance(balance)
                .withCurrency(currency)
                .withBirthDate(birthDate)
                .build();
    }

    @Override
    public void decreasePlayerBalance(Player player, double amount) {
        if (player.getBalance() < amount) throw new IllegalArgumentException("Not enough money");
        player.setBalance(player.getBalance() - amount);
    }

    @Override
    public void increasePlayerBalance(Player player, double amount) {
        player.setBalance(player.getBalance() + amount);
    }

    @Override
    public boolean canMakeWager(Player player, double wage) {
        return player.getBalance() >= wage;
    }
}
