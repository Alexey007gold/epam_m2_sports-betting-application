package com.epam.training.sportsbetting.converter.modelmapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDate;

public class MMStringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
        return LocalDate.parse(mappingContext.getSource());
    }
}
