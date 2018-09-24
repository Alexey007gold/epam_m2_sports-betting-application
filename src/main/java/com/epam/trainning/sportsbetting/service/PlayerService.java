package com.epam.trainning.sportsbetting.service;

import com.epam.trainning.sportsbetting.domain.user.Currency;
import com.epam.trainning.sportsbetting.domain.user.Player;

import java.time.LocalDate;

public class PlayerService {

    public PlayerService() {
    }

    public Player registerPlayer(String name, String accNum, double balance, Currency currency, LocalDate birthDate) {
        return new Player(name, accNum, balance, currency, birthDate);
    }

    public void decreasePlayerBalance(Player player, double amount) {
        if (player.getBalance() < amount) throw new IllegalArgumentException("Not enough money");
        player.setBalance(player.getBalance() - amount);
    }

    public void increasePlayerBalance(Player player, double amount) {
        player.setBalance(player.getBalance() + amount);
    }

    public boolean canMakeWager(Player player, double wage) {
        return player.getBalance() >= wage;
    }
}
