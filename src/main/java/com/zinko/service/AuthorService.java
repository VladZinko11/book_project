package com.zinko.service;

import com.zinko.service.dto.AuthorDto;
import com.zinko.service.dto.AuthorSimpleDto;

import java.util.List;

public interface AuthorService {

    AuthorDto create(AuthorSimpleDto authorSimpleDto);

    List<AuthorSimpleDto> getAll(int page, int size);

    AuthorDto getById(Long id);

    void delete(Long id);

    AuthorDto update(AuthorDto authorDto);
}
