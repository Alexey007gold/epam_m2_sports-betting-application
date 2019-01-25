package com.epam.training.sportsbetting.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "bet")
@Entity
@Table(name = "outcome")
public class OutcomeEntity extends AbstractEntity {

    @Column(name = "result_id")
    private Integer resultId;
    @ManyToOne
    @JoinColumn(name = "bet_id", nullable = false)
    private BetEntity bet;
    @Column(name = "value", nullable = false)
    private String value;
    @OneToMany(mappedBy = "outcomeId", cascade = CascadeType.ALL)
    private List<OutcomeOddEntity> outcomeOdds;
}
