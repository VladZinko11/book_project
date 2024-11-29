package com.zinko.service.impl;

import com.zinko.config.CustomMessageSource;
import com.zinko.data.model.User;
import com.zinko.data.repository.UserRepository;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserAuthDto;
import com.zinko.service.dto.UserDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String NOT_FOUND_USER_WITH_ID_MESSAGE = "not.found.user.with.id.message";
    private static final String NOT_FOUND_USER_WITH_EMAIL_MESSAGE = "not.found.user.with.email.message";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CustomMessageSource messageSource;

    @Override
    public UserDto create(UserAuthDto userAuthDto) {
        User user = userMapper.toEntity(userAuthDto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_USER_WITH_ID_MESSAGE, new Object[]{id})));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        return userRepository.findAll(pageRequest).stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto update(UserAuthDto userAuthDto) {
        if (!userRepository.existsById(userAuthDto.getId())) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_USER_WITH_ID_MESSAGE, new Object[]{userAuthDto.getId()}));
        }
        User user = userMapper.toEntity(userAuthDto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return email -> {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            return optionalUser.orElseThrow(() ->
                    new NotFoundException(messageSource.getMessage(NOT_FOUND_USER_WITH_EMAIL_MESSAGE, new Object[]{email})));
        };
    }
}
