package com.zinko.service;

import java.util.Locale;

public interface CustomMessageSource {

    String getMessage(String code);

    String getMessage(String code, Object[] args);

    void setLocale(Locale locale);
}
