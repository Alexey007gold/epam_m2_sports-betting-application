package com.epam.training.sportsbetting.converter.spring;

import com.epam.training.sportsbetting.domain.user.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCurrencyConverter implements Converter<String, Currency> {

    @Override
    public Currency convert(String source) {
        return Currency.valueOf(source);
    }
}