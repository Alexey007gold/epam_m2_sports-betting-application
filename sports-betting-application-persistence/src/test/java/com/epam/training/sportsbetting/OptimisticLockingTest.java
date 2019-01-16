package com.epam.training.sportsbetting;

import com.epam.training.sportsbetting.config.LiquibaseConfig;
import com.epam.training.sportsbetting.config.PersistentJPAConfig;
import com.epam.training.sportsbetting.entity.PlayerEntity;
import com.epam.training.sportsbetting.enums.Currency;
import com.epam.training.sportsbetting.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration(classes = {PersistentJPAConfig.class, LiquibaseConfig.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class OptimisticLockingTest {

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    public void init() {
        PlayerEntity player = PlayerEntity.builder()
                .enabled(true)
                .name("John")
                .accountNumber("1234")
                .email("john@gmail.com")
                .password("pass".toCharArray())
                .balance(1000)
                .currency(Currency.EUR)
                .birthDate(LocalDate.now())
                .build();
        playerRepository.save(player);
    }

    @Test
    public void shouldThrowException() {
        PlayerEntity playerEntity = playerRepository.findById(1).get();

        playerEntity.setBalance(50);

        playerRepository.save(playerEntity);
        assertThrows(ObjectOptimisticLockingFailureException.class, () -> playerRepository.save(playerEntity));

    }
}
