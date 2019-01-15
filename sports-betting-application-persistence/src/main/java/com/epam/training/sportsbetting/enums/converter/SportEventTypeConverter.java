package com.epam.training.sportsbetting.enums.converter;

import com.epam.training.sportsbetting.enums.SportEventType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SportEventTypeConverter implements AttributeConverter<SportEventType, Character> {

    @Override
    public Character convertToDatabaseColumn(SportEventType attribute) {
        switch (attribute) {
            case FOOTBALL:
                return 'F';
            case TENNIS:
                return 'T';
            default:
                throw new IllegalArgumentException("Unknown " + attribute);
        }
    }

    @Override
    public SportEventType convertToEntityAttribute(Character dbData) {
        switch (dbData) {
            case 'F':
                return SportEventType.FOOTBALL;
            case 'T':
                return SportEventType.TENNIS;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}