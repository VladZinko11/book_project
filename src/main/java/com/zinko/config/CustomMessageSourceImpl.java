package com.zinko.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CustomMessageSourceImpl implements CustomMessageSource {
    private final MessageSource messageSource;
    private Locale locale;

    @Override
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, locale);
    }

    @Override
    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, locale);
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
