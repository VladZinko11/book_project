package com.zinko.gateway.controller;

import com.zinko.gateway.service.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    @GetMapping("/fallback")
    public ErrorDto fallback() {
        String message = "service temporality unavailable";
        return new ErrorDto(message, HttpStatus.SERVICE_UNAVAILABLE.toString());
    }
}
