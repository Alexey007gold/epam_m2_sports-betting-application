package com.epam.training.sportsbetting.service.impl;

import com.epam.training.sportsbetting.service.I18NService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class I18NServiceImpl implements I18NService {

    private final MessageSource messageSource;

    @Autowired
    public I18NServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }
}
