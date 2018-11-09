package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;

import java.time.LocalDate;

public interface PlayerService {
    /**
     * Create a new player object
     * Does not save to anywhere yet
     * @param name
     * @param accNum
     * @param balance
     * @param currency
     * @param birthDate
     * @return
     */
    Player registerPlayer(String name, String accNum, double balance, Currency currency, LocalDate birthDate);

    /**
     * Decreases the balance of the given player by the given value
     * @param player
     * @param amount
     */
    void decreasePlayerBalance(Player player, double amount);

    /**
     * Increases the balance of the given player by the given value
     * @param player
     * @param amount
     */
    void increasePlayerBalance(Player player, double amount);

    /**
     * Checks whether a given player can make a wage of the given size
     * @param player
     * @param wage
     * @return
     */
    boolean canMakeWager(Player player, double wage);
}
