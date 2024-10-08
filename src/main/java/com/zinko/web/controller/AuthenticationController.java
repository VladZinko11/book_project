package com.zinko.web.controller;

import com.zinko.service.AuthenticationService;
import com.zinko.service.ErrorService;
import com.zinko.service.dto.UserAuthDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final ErrorService errorService;

    @PostMapping("/signing")
    public void signIn(@RequestBody @Valid UserAuthDto userAuthDto, BindingResult errors, HttpServletResponse response) {
        errorService.checkErrors(errors);
        String jwt = authenticationService.signIn(userAuthDto);
        response.setHeader("Authorization", jwt);
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid UserAuthDto userAuthDto, BindingResult errors, HttpServletResponse response) {
        errorService.checkErrors(errors);
        String jwt = authenticationService.signUp(userAuthDto);
        response.setHeader("Authorization", jwt);
    }
}
