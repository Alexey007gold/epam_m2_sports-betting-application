package com.epam.training.sportsbetting.enums.converter;

import com.epam.training.sportsbetting.enums.BetType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BetTypeConverter implements AttributeConverter<BetType, Character> {

    @Override
    public Character convertToDatabaseColumn(BetType attribute) {
        switch (attribute) {
        case GOAL:
            return 'G';
        case SCORE:
            return 'S';
        case WINNER:
            return 'W';
        default:
            throw new IllegalArgumentException("Unknown " + attribute);
        }
    }

    @Override
    public BetType convertToEntityAttribute(Character dbData) {
        switch (dbData) {
        case 'G':
            return BetType.GOAL;
        case 'S':
            return BetType.SCORE;
        case 'W':
            return BetType.WINNER;
        default:
            throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}