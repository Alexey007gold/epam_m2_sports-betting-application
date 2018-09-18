package com.epam.trainning.sportsbetting.domain.sportevent;

import java.time.LocalDateTime;

public class TennisSportEvent extends SportEvent {

    public TennisSportEvent() {
    }

    public TennisSportEvent(String title, LocalDateTime startDate, LocalDateTime endDate) {
        super(title, startDate, endDate);
    }
}
