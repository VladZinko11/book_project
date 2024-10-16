package com.zinko.serviceuser.service.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

public class ValidationException extends BookProjectException {

    @Getter
    private final Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }
}
