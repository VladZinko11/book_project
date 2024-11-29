package com.zinko.service;

import com.zinko.service.dto.AuthorCreateDto;
import com.zinko.service.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    AuthorDto create(AuthorCreateDto authorCreateDto);

    List<AuthorDto> getAll();

    AuthorDto getById(Long id);

    void delete(Long id);

    void update(AuthorCreateDto authorCreateDto);
}
