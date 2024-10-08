package com.zinko.service.impl;

import com.zinko.data.model.User;
import com.zinko.service.AuthenticationService;
import com.zinko.service.JwtService;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserAuthDto;
import com.zinko.service.dto.UserDto;
import com.zinko.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String signUp(UserAuthDto userAuthDto) {
        userAuthDto.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
        UserDto userDto = userService.create(userAuthDto);
        User user = userMapper.toEntity(userDto);
        return jwtService.generateToken(user);
    }

    @Override
    public String signIn(UserAuthDto userAuthDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userAuthDto.getEmail(), userAuthDto.getPassword()));

        User user = (User) userService.userDetailsService().loadUserByUsername(userAuthDto.getEmail());
        return jwtService.generateToken(user);
    }
}
