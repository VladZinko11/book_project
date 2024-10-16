package com.zinko.serviceuser.service.impl;

import com.zinko.serviceuser.data.model.User;
import com.zinko.serviceuser.service.AuthenticationService;
import com.zinko.serviceuser.service.JwtService;
import com.zinko.serviceuser.service.UserService;
import com.zinko.serviceuser.service.dto.UserAuthDto;
import com.zinko.serviceuser.service.dto.UserDto;
import com.zinko.serviceuser.service.mapper.UserMapper;
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
