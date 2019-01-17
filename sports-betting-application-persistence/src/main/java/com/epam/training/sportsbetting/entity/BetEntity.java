package com.epam.training.sportsbetting.entity;

import com.epam.training.sportsbetting.enums.BetType;
import com.epam.training.sportsbetting.enums.converter.BetTypeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "event")
@ToString(exclude = "event")
@Entity
@Table(name = "bet")
public class BetEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "sport_event_id", nullable = false)
    private SportEventEntity event;
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(mappedBy = "bet", cascade = CascadeType.ALL)
    private List<OutcomeEntity> outcomes;
    @Convert(converter = BetTypeConverter.class)
    @Column(name = "bet_type", nullable = false)
    private BetType betType;

}
