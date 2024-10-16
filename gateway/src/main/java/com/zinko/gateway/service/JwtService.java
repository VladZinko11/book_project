package com.zinko.gateway.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String jwt);

    boolean isTokenValid(String jwt, UserDetails userDetails);
}
