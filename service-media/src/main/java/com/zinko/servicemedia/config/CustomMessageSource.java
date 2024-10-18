package com.zinko.servicemedia.config;


public interface CustomMessageSource {

    String getMessage(String code);

    String getMessage(String code, Object[] args);

}
