package com.zinko.servicebook.service.impl;

import com.zinko.servicebook.config.CustomMessageSource;
import com.zinko.servicebook.data.model.Genre;
import com.zinko.servicebook.data.repository.GenreRepository;
import com.zinko.servicebook.service.GenreService;
import com.zinko.servicebook.service.dto.GenreDto;
import com.zinko.servicebook.service.exception.NotFoundException;
import com.zinko.servicebook.service.mapper.GenreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {
    public static final String NOT_FOUND_GENRE_WITH_ID_MESSAGE = "not.found.genre.with.id.message";

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final CustomMessageSource messageSource;

    @Override
    public GenreDto create(GenreDto genreDto) {
        Genre genre = genreMapper.toEntity(genreDto);
        Genre saved = genreRepository.save(genre);
        return genreMapper.toDto(saved);
    }

    @Override
    public List<GenreDto> getAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .toList();
    }


    @Override
    public GenreDto getById(Long id) {
        Optional<Genre> optionalGenreDao = genreRepository.findById(id);
        Genre genre = optionalGenreDao.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_GENRE_WITH_ID_MESSAGE, new Object[]{id})));
        return genreMapper.toDto(genre);
    }

    @Override
    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public GenreDto update(GenreDto genreDto) {
        if (!genreRepository.existsById(genreDto.getId())) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_GENRE_WITH_ID_MESSAGE, new Object[]{genreDto.getId()}));
        }
        Genre genre = genreMapper.toEntity(genreDto);
        Genre saved = genreRepository.save(genre);
        return genreMapper.toDto(saved);
    }
}
