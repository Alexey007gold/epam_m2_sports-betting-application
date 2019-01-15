package com.epam.training.sportsbetting.entity;

import com.epam.training.sportsbetting.enums.Currency;
import com.epam.training.sportsbetting.enums.converter.CurrencyTypeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wager")
public class WagerEntity extends AbstractEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sport_event_id")
    private SportEventEntity event;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    private PlayerEntity player;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "outcome_odd_id")
    private OutcomeOddEntity outcomeOdd;
    @Column(name = "amount", nullable = false)
    private double amount;
    @Convert(converter = CurrencyTypeConverter.class)
    @Column(name = "currency", nullable = false)
    private Currency currency;
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    @Column(name = "processed", nullable = false)
    private boolean processed;
    @Column(name = "winner")
    private boolean winner;
}
