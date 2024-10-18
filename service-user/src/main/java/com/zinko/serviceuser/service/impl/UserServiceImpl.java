package com.zinko.serviceuser.service.impl;

import com.zinko.serviceuser.config.CustomMessageSource;
import com.zinko.serviceuser.data.model.User;
import com.zinko.serviceuser.data.repository.UserRepository;
import com.zinko.serviceuser.service.UserService;
import com.zinko.serviceuser.service.dto.UserAuthDto;
import com.zinko.serviceuser.service.dto.UserDto;
import com.zinko.serviceuser.service.exception.BadCredentialsException;
import com.zinko.serviceuser.service.exception.NotFoundException;
import com.zinko.serviceuser.service.mapper.UserMapper;
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
    private static final String ENTER_VALID_EMAIL_MESSAGE = "enter.valid.email.message";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CustomMessageSource messageSource;

    @Override
    public UserDto create(UserAuthDto userAuthDto) {
        emailValidation(userAuthDto);
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
        emailValidation(userAuthDto);
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

    private void emailValidation(UserAuthDto userAuthDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userAuthDto.getEmail());
        if(optionalUser.isEmpty()) {
            return;
        }
        if(!userAuthDto.getId().equals(optionalUser.get().getId())) {
            throw new BadCredentialsException(messageSource.getMessage(ENTER_VALID_EMAIL_MESSAGE));
        }
    }
}
