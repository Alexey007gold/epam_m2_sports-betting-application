package com.epam.training.sportsbetting.entity;

import com.epam.training.sportsbetting.enums.SportEventType;
import com.epam.training.sportsbetting.enums.converter.SportEventTypeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sport_event")
public class SportEventEntity extends AbstractEntity {

    @Convert(converter = SportEventTypeConverter.class)
    @Column(name = "event_type", nullable = false)
    private SportEventType eventType;
    @Size(min = 3)
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<BetEntity> bets;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id")
    private ResultEntity result;
}
