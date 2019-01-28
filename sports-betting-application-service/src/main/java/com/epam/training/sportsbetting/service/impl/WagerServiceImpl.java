package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.domain.user.Player;
import com.epam.training.sportsbetting.domain.wager.Wager;
import com.epam.training.sportsbetting.entity.*;
import com.epam.training.sportsbetting.enums.Currency;
import com.epam.training.sportsbetting.repository.OutcomeRepository;
import com.epam.training.sportsbetting.repository.SportEventRepository;
import com.epam.training.sportsbetting.repository.WagerRepository;
import com.epam.training.sportsbetting.service.OutcomeService;
import com.epam.training.sportsbetting.service.PlayerService;
import com.epam.training.sportsbetting.service.WagerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WagerServiceImpl implements WagerService {

    private final PlayerService playerService;
    private final OutcomeService outcomeService;
    private final OutcomeRepository outcomeRepository;
    private final WagerRepository wagerRepository;
    private final SportEventRepository eventRepository;

    private final ModelMapper mapper;

    private final EntityManager entityManager;

    @Autowired
    public WagerServiceImpl(PlayerService playerService, OutcomeService outcomeService,
                            OutcomeRepository outcomeRepository, WagerRepository wagerRepository,
                            SportEventRepository eventRepository, ModelMapper mapper, EntityManager entityManager) {
        this.playerService = playerService;
        this.outcomeService = outcomeService;
        this.outcomeRepository = outcomeRepository;
        this.wagerRepository = wagerRepository;
        this.eventRepository = eventRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Wager> getWagersByPlayer(Player player) {
        return getWagersByPlayerEmail(player.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Wager> getWagersByPlayerId(Integer id) {
        return wagerRepository.findByPlayerId(id).stream()
                .map((w) -> mapper.map(w, Wager.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Wager> getWagersByPlayerEmail(String email) {
        return wagerRepository.findByPlayerEmail(email).stream()
                .map((w) -> mapper.map(w, Wager.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean newWager(Integer playerId, Integer outcomeId, Double amount) {
        Player player = playerService.getPlayerById(playerId).orElseThrow(IllegalArgumentException::new);
        OutcomeEntity outcomeById = outcomeRepository.findById(outcomeId).orElseThrow();
        SportEventEntity sportEventEntity = eventRepository.getByBetsOutcomesId(outcomeId)
                .orElseThrow(() -> new IllegalStateException("Not found event for the outcomeId"));
        LocalDateTime currentTime = LocalDateTime.now();
        if (sportEventEntity.getStartDate().isBefore(currentTime) || sportEventEntity.getResult() != null)
            throw new IllegalStateException("The event is already played");

        OutcomeOddEntity activeOdd = Objects.requireNonNull(outcomeService
                .getActiveOddEntity(outcomeById.getOutcomeOdds(), sportEventEntity.getStartDate()));
        wagerRepository.save(WagerEntity.builder()
                .amount(amount)
                .currency(Currency.valueOf(player.getCurrency().toString()))
                .timestamp(currentTime)
                .outcomeOdd(activeOdd)
                .event(outcomeById.getBet().getEvent())
                .player(entityManager.getReference(PlayerEntity.class, playerId))
                .build());
        playerService.decreaseBalanceByPlayerId(playerId, amount);
        return true;
    }

    @Override
    @Transactional
    public boolean removeWager(Integer playerId, Integer wagerId) {
        WagerEntity wager = wagerRepository.findById(wagerId).orElseThrow(() -> new IllegalArgumentException("Wager was not found"));
        int deletedRows = wagerRepository.deleteByIdAndPlayerId(wagerId, playerId);
        if (deletedRows == 1) {
            playerService.increaseBalanceByPlayerId(playerId, wager.getAmount() * 0.75);
            return true;
        } else throw new IllegalArgumentException("Wager not found");
    }

    @Override
    @Transactional
    public Map<Integer, Double> processWagers(Set<Integer> playedEventsIds) {
        List<WagerEntity> wagerEntities = wagerRepository.findByEventIdInAndProcessedFalse(playedEventsIds);

        Map<Integer, Double> playerIdToPrize = new HashMap<>();

        wagerEntities.forEach(w -> {
            w.setProcessed(true);
            List<OutcomeEntity> realOutcomes = w.getEvent().getResult().getOutcomes();
            Integer expectedOutcomeId = w.getOutcomeOdd().getOutcomeId();
            for (OutcomeEntity realOutcome : realOutcomes) {
                if (realOutcome.getId().equals(expectedOutcomeId)) {
                    w.setWinner(true);
                    double prize = calculatePrize(w);
                    playerIdToPrize.merge(w.getPlayer().getId(), prize, Double::sum);
                    break;
                }
            }
        });

        playerIdToPrize.forEach(playerService::increaseBalanceByPlayerId);
        return playerIdToPrize;
    }

    @Override
    public double calculatePrize(WagerEntity wager) {
        double prize = wager.getAmount() * wager.getOutcomeOdd().getValue();

        String str = String.valueOf(prize);
        int pointIndex = str.indexOf('.');
        int truncateToIndex = Math.min(pointIndex + 3, str.length());
        return Double.valueOf(str.substring(0, truncateToIndex));
    }
}
