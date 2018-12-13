package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.service.PlayerService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private Map<Integer, Player> playerMap;

    @PostConstruct
    public void init() {
        playerMap = new HashMap<>();
        String email = "arnold@gmail.com";
        playerMap.put(1, new Player.Builder()
            .withId(1)
            .withEmail(email)
            .withPassword("1234".toCharArray())
            .withAccountNumber("1234")
            .withBalance(1000)
            .withCurrency(Currency.EUR)
            .withName("Arnold Schwarzenegger")
            .withBirthDate(LocalDate.of(1941, 1, 2))
            .build());
    }

    @Override
    public Player registerPlayer(String name, String accNum, double balance, Currency currency, LocalDate birthDate) {
        return new Player.Builder()
                .withId(1)
                .withEnabled(true)
                .withName(name)
                .withAccountNumber(accNum)
                .withBalance(balance)
                .withCurrency(currency)
                .withBirthDate(birthDate)
                .build();
    }

    @Override
    public Optional<Player> getPlayerById(Integer id) {
        return Optional.ofNullable(playerMap.get(id));
    }

    @Override
    public Optional<Player> getPlayerByEmail(String email) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Player> updatePlayerByEmail(Player player) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Player> updatePlayerById(Player player) {
        return Optional.ofNullable(playerMap.computeIfPresent(player.getId(), (key, oldVal) -> player));
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
