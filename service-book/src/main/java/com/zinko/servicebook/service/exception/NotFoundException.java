package com.zinko.servicebook.service.exception;

public class NotFoundException extends BookProjectException {
    public NotFoundException(String message) {
        super(message);
    }
}