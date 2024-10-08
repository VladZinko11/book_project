package com.zinko.service;

import com.zinko.service.dto.UserAuthDto;

public interface AuthenticationService {
    String signUp(UserAuthDto userAuthDto);

    String signIn(UserAuthDto userAuthDto);
}
