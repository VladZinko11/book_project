package com.zinko.serviceuser.web.controller;

import com.zinko.serviceuser.config.CustomMessageSource;
import com.zinko.serviceuser.service.ErrorService;
import com.zinko.serviceuser.service.dto.ErrorDto;
import com.zinko.serviceuser.service.dto.ValidationResultDto;
import com.zinko.serviceuser.service.exception.NotFoundException;
import com.zinko.serviceuser.service.exception.ServerException;
import com.zinko.serviceuser.service.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ErrorController {
    public static final String SERVER_ERROR_MESSAGE = "server.error.message";
    private final CustomMessageSource messageSource;
    private final ErrorService errorService;

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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto error(ValidationException e) {
        Map<String, List<String>> messages = errorService.mapErrors(e.getErrors());
        return new ValidationResultDto(messages);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto error(AuthorizationDeniedException e) {
        return new ErrorDto("FORBIDDEN", "Access denied");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto error(AuthenticationException e) {
        return new ErrorDto("UNAUTHORIZED", "Unauthorized");
    }
}
