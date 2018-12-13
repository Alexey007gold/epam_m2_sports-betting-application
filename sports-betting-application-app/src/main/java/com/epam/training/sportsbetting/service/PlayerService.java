package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;

import java.time.LocalDate;
import java.util.Optional;

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
     * Returns a player object by it's id
     * @param id
     * @return
     */
    Optional<Player> getPlayerById(Integer id);

    /**
     * Returns a player object by it's email
     * @param email
     * @return
     */
    Optional<Player> getPlayerByEmail(String email);

    /**
     * Updates the player data
     * @param player
     * @return
     */
    Optional<Player> updatePlayerByEmail(Player player);

    /**
     * Updates the player data
     * @param player
     * @return
     */
    Optional<Player> updatePlayerById(Player player);

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
