package com.zinko.service;

import com.zinko.service.dto.GenreDto;

import java.util.List;

public interface GenreService {
    GenreDto create(GenreDto genreDto);

    List<GenreDto> getAll();

    GenreDto getById(Long id);

    void delete(Long id);

    GenreDto update(GenreDto genreDto);

}
