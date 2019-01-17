package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.PageDTO;
import com.epam.training.sportsbetting.domain.user.Currency;
import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.entity.PlayerEntity;
import com.epam.training.sportsbetting.form.UpdatePlayerForm;
import com.epam.training.sportsbetting.repository.PlayerRepository;
import com.epam.training.sportsbetting.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@DependsOn("liquibase")
public class PlayerServiceImpl implements PlayerService, InitializingBean {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Player registerPlayer(String name, String accNum, double balance, Currency currency, LocalDate birthDate) {
        PlayerEntity player = PlayerEntity.builder()
            .enabled(true)
            .name(name)
            .accountNumber(accNum)
            .balance(balance)
            .currency(com.epam.training.sportsbetting.enums.Currency.valueOf(currency.name()))
            .birthDate(birthDate)
            .build();
        playerRepository.save(player);
        return modelMapper.map(player, Player.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Player> getPlayerById(Integer id) {
        return playerRepository.findById(id).map(p -> modelMapper.map(p, Player.class));
    }

    @Override
    public PageDTO<Player> listPlayers(Pageable pageable) {
        return PageDTO.of(playerRepository.findAll(pageable).map(p -> modelMapper.map(p, Player.class)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Player> getPlayerByEmail(String email) {
        return playerRepository.findByEmail(email).map(p -> modelMapper.map(p, Player.class));
    }

    @Override
    @Transactional
    public Player updatePlayerByEmail(String email, UpdatePlayerForm form) {
        PlayerEntity player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException(String.format("User with email %s not found", email)));
        modelMapper.map(form, player);
        return modelMapper.map(playerRepository.save(player), Player.class);
    }

    @Override
    @Transactional
    public Player updatePlayerById(Integer id, UpdatePlayerForm form) {
        PlayerEntity player = playerRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException(String.format("User with id %d not found", id)));
        modelMapper.map(form, player);
        return modelMapper.map(playerRepository.save(player), Player.class);
    }

    @Override
    public void decreasePlayerBalance(Player player, double amount) {
        if (player.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough money");
        }
        player.setBalance(player.getBalance() - amount);
    }

    private void decreasePlayerBalance(PlayerEntity player, double amount) {
        if (player.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough money");
        }
        player.setBalance(player.getBalance() - amount);
    }

    @Override
    @Transactional
    public void decreaseBalanceByPlayerId(Integer id, double amount) {
        PlayerEntity player = playerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        decreasePlayerBalance(player, amount);
        playerRepository.save(player);
    }

    @Override
    public void increasePlayerBalance(Player player, double amount) {
        player.setBalance(player.getBalance() + amount);
    }

    private void increasePlayerBalance(PlayerEntity player, double amount) {
        player.setBalance(player.getBalance() + amount);
    }

    @Override
    @Transactional
    public void increaseBalanceByPlayerId(Integer id, double amount) {
        PlayerEntity player = playerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        increasePlayerBalance(player, amount);
        playerRepository.save(player);
    }

    @Override
    public boolean canMakeWager(Player player, double wage) {
        return player.getBalance() >= wage;
    }

    private void loadPlayers() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getClassLoader().getResource("users.tsv").toURI()));

        for (String line : lines) {
            try {
                String[] values = line.split("\t");

                PlayerEntity player = PlayerEntity.builder()
                    .enabled(true)
                    .email(values[0])
                    .password(passwordEncoder.encode(values[1]).toCharArray())
                    .accountNumber(values[2])
                    .balance(Double.parseDouble(values[3]))
                    .currency(com.epam.training.sportsbetting.enums.Currency.valueOf(values[4]))
                    .name(values[5])
                    .birthDate(LocalDate.parse(values[6]))
                    .build();
                playerRepository.save(player);
            } catch (DataIntegrityViolationException e) {
                //already present
            }
        }
    }

    @Override
    @Transactional
    public void afterPropertiesSet() throws IOException, URISyntaxException {
        //PostConstruct alternative for java 9+
        loadPlayers();
    }
}
