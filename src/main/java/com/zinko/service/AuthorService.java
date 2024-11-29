package com.zinko.service;

import com.zinko.service.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    AuthorDto create(AuthorDto authorDto);

    List<AuthorDto> getAll();

    AuthorDto getById(Long id);

    void delete(Long id);

    void update(AuthorDto authorDto);
}
