package com.epam.training.sportsbetting.converter.modelmapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeToLongConverter implements Converter<LocalDateTime, Long> {
    @Override
    public Long convert(MappingContext<LocalDateTime, Long> mappingContext) {
        return mappingContext.getSource().toEpochSecond(ZoneOffset.UTC);
    }
}
