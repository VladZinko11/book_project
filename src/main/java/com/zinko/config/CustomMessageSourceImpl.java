package com.zinko.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CustomMessageSourceImpl implements CustomMessageSource {
    private final MessageSource messageSource;

    @Override
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, getCurrentLocale());
    }

    @Override
    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, getCurrentLocale());
    }

    private Locale getCurrentLocale() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            return request.getLocale();
        }
        return Locale.ENGLISH;
    }
}
