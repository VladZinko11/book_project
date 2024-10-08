package com.zinko.service;

import com.zinko.service.dto.UserAuthDto;
import com.zinko.service.dto.UserDto;
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
