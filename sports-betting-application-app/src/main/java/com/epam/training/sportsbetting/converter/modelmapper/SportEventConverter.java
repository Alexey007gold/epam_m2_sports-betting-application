package com.epam.training.sportsbetting.converter.modelmapper;

import com.epam.training.sportsbetting.domain.sportevent.FootballSportEvent;
import com.epam.training.sportsbetting.domain.sportevent.SportEvent;
import com.epam.training.sportsbetting.domain.sportevent.TennisSportEvent;
import com.epam.training.sportsbetting.entity.SportEventEntity;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class SportEventConverter implements Converter<SportEventEntity, SportEvent> {
    @Override
    public SportEvent convert(MappingContext<SportEventEntity, SportEvent> context) {
        Class<? extends SportEvent> destinationClass;
        switch (context.getSource().getEventType()) {
            case FOOTBALL:
                destinationClass = FootballSportEvent.class;
                break;
            case TENNIS:
                destinationClass = TennisSportEvent.class;
                break;
            default:
                throw new IllegalArgumentException("Event type unknown: " + context.getSource().getEventType());
        }
        return context.getMappingEngine().map(context.create(context.getSource(), destinationClass));
    }
}
