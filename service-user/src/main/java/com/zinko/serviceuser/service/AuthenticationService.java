package com.zinko.serviceuser.service;

import com.zinko.serviceuser.service.dto.UserAuthDto;

public interface AuthenticationService {
    String signUp(UserAuthDto userAuthDto);

    String signIn(UserAuthDto userAuthDto);
}
