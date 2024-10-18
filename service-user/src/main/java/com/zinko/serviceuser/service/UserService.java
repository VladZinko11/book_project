package com.zinko.serviceuser.service;

import com.zinko.serviceuser.service.dto.UserAuthDto;
import com.zinko.serviceuser.service.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    UserDto create(UserAuthDto userAuthDto);

    UserDto getById(Long id);

    List<UserDto> getAll(int page, int size);

    UserDto update(UserAuthDto userAuthDto);

    void delete(Long id);

    UserDetailsService userDetailsService();

}
