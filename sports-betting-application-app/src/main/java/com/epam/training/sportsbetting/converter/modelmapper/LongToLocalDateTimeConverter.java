package com.epam.training.sportsbetting.converter.modelmapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {
    @Override
    public LocalDateTime convert(MappingContext<Long, LocalDateTime> mappingContext) {
        return LocalDateTime.ofEpochSecond(mappingContext.getSource(), 0, ZoneOffset.UTC);
    }
}
