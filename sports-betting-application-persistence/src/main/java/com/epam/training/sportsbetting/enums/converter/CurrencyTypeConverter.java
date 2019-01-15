package com.epam.training.sportsbetting.enums.converter;

import com.epam.training.sportsbetting.enums.Currency;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CurrencyTypeConverter implements AttributeConverter<Currency, Character> {

    @Override
    public Character convertToDatabaseColumn(Currency attribute) {
        switch (attribute) {
            case EUR:
                return 'E';
            case HUF:
                return 'H';
            case USD:
                return 'U';
            default:
                throw new IllegalArgumentException("Unknown " + attribute);
        }
    }

    @Override
    public Currency convertToEntityAttribute(Character dbData) {
        switch (dbData) {
            case 'E':
                return Currency.EUR;
            case 'H':
                return Currency.HUF;
            case 'U':
                return Currency.USD;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}