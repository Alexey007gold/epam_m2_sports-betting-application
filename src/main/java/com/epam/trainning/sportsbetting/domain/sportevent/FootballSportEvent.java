package com.epam.trainning.sportsbetting.domain.sportevent;

import java.time.LocalDateTime;

public class FootballSportEvent extends SportEvent {

    public FootballSportEvent() {
    }

    public FootballSportEvent(String title, LocalDateTime startDate, LocalDateTime endDate) {
        super(title, startDate, endDate);
    }
}
