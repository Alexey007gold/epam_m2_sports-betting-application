package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    void shouldReturnCorrectResultOnRegisterPlayer() {
        String name = "John";
        String accNum = "1234";
        int balance = 1000;
        Currency currency = Currency.EUR;
        LocalDate date = LocalDate.MIN;

        Player player = playerService.registerPlayer(name, accNum, balance, currency, date);

        assertEquals(name, player.getName());
        assertEquals(accNum, player.getAccountNumber());
        assertEquals(balance, player.getBalance());
        assertEquals(currency, player.getCurrency());
        assertEquals(date, player.getBirthDate());
        assertTrue(player.isEnabled());
        assertNull(player.getEmail());
        assertNull(player.getPassword());
    }

    @Test
    void shouldChangeBalanceCorrectlyOnDecreasePlayerBalance() {
        String name = "John";
        String accNum = "1234";
        int balance = 1000;
        Currency currency = Currency.EUR;
        LocalDate date = LocalDate.MIN;

        Player player = playerService.registerPlayer(name, accNum, balance, currency, date);
        playerService.decreasePlayerBalance(player, 5.5);

        assertEquals(994.5, player.getBalance());
    }

    @Test
    void shouldThrowExceptionOnDecreasePlayerBalance() {
        String name = "John";
        String accNum = "1234";
        int balance = 5;
        Currency currency = Currency.EUR;
        LocalDate date = LocalDate.MIN;

        Player player = playerService.registerPlayer(name, accNum, balance, currency, date);

        assertThrows(IllegalArgumentException.class, () -> playerService.decreasePlayerBalance(player, 5.1));
    }

    @Test
    void shouldChangeBalanceCorrectlyOnIncreasePlayerBalance() {
        String name = "John";
        String accNum = "1234";
        int balance = 1000;
        Currency currency = Currency.EUR;
        LocalDate date = LocalDate.MIN;

        Player player = playerService.registerPlayer(name, accNum, balance, currency, date);
        playerService.increasePlayerBalance(player, 5.5);

        assertEquals(1005.5, player.getBalance());
    }

    @Test
    void shouldReturnCorrectResultOnCanMakeWager() {
        String name = "John";
        String accNum = "1234";
        int balance = 20;
        Currency currency = Currency.EUR;
        LocalDate date = LocalDate.MIN;

        Player player = playerService.registerPlayer(name, accNum, balance, currency, date);
        assertTrue(playerService.canMakeWager(player, 19));
        assertTrue(playerService.canMakeWager(player, 20));
        assertFalse(playerService.canMakeWager(player, 20.1));
    }
}