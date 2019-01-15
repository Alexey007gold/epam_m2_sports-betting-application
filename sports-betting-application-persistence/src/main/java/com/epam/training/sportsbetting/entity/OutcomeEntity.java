package com.epam.training.sportsbetting.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "outcome")
public class OutcomeEntity extends AbstractEntity {

    @Column(name = "result_id")
    private Integer resultId;
    @Column(name = "bet_id", nullable = false)
    private Integer betId;
    @Column(name = "value", nullable = false)
    private String value;
    @OneToMany(mappedBy = "outcomeId", cascade = CascadeType.ALL)
    private List<OutcomeOddEntity> outcomeOdds;
}
