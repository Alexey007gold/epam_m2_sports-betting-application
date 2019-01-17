package com.epam.training.sportsbetting.form;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SportEventForm {

    private SportEventType eventType;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<BetForm> bets;
    private ResultForm result;
}
