package com.zinko.serviceuser.config;


public interface CustomMessageSource {

    String getMessage(String code);

    String getMessage(String code, Object[] args);

}
