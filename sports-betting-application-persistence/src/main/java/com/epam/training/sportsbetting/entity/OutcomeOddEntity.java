package com.epam.training.sportsbetting.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "outcome_odd")
public class OutcomeOddEntity extends AbstractEntity {

    @Column(name = "outcome_id", nullable = false)
    private Integer outcomeId;
    @Column(name = "value", nullable = false)
    private double value;
    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;
    @Column(name = "valid_to")
    private LocalDateTime validTo;
}
