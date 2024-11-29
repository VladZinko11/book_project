package com.zinko.service.impl;

import com.zinko.data.model.Genre;
import com.zinko.data.repository.GenreRepository;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.GenreService;
import com.zinko.service.dto.GenreDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.mapper.GenreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {
    public static final String NOT_FOUND_GENRE_WITH_ID_MESSAGE = "not.found.entity.with.id.message";

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
        return genreRepository.getAll().stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    public List<GenreDto> getByBookId(Long id) {
        return genreRepository.getByBookId(id).stream()
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    public GenreDto getById(Long id) {
        Optional<Genre> optionalGenreDao = genreRepository.getById(id);
        Genre genre = optionalGenreDao.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_GENRE_WITH_ID_MESSAGE, new Object[]{id})));
        return genreMapper.toDto(genre);
    }

    @Override
    public void delete(Long id) {
        if (!genreRepository.delete(id)) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_GENRE_WITH_ID_MESSAGE, new Object[]{id}));
        }
    }

    @Override
    public void update(GenreDto genreDto) {
        Genre genre = genreMapper.toEntity(genreDto);
        genreRepository.update(genre);
    }
}
