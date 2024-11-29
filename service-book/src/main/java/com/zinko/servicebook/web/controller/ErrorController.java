package com.zinko.servicebook.web.controller;

import com.zinko.servicebook.config.CustomMessageSource;
import com.zinko.servicebook.service.dto.ErrorDto;
import com.zinko.servicebook.service.exception.NotFoundException;
import com.zinko.servicebook.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ErrorController {
    public static final String SERVER_ERROR_MESSAGE = "server.error.message";
    private final CustomMessageSource messageSource;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto error(NotFoundException e) {
        return new ErrorDto("Client error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto error(ServerException e) {
        return new ErrorDto("Server error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto error(Exception e) {
        log.error(e.getMessage());
        return new ErrorDto("Server error",
                messageSource.getMessage(SERVER_ERROR_MESSAGE));
    }

}
