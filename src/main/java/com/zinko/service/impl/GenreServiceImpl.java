package com.zinko.service.impl;

import com.zinko.data.GenreRepository;
import com.zinko.data.dao.GenreDao;
import com.zinko.model.Genre;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.GenreService;
import com.zinko.service.dto.GenreDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.exception.ServerException;
import com.zinko.service.mapper.GenreMapper;
import com.zinko.service.mapper.dao.impl.GenreDaoMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    public static final String SERVER_ERROR = "server.error.message";
    public static final String NOT_FOUND_BOOK_WITH_ID_MESSAGE = "not.found.book.with.id.message";

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final GenreDaoMapperImpl genreDaoMapper;
    private final CustomMessageSource messageSource;

    @Override
    public GenreDto create(GenreDto genreDto) {
        Genre genre = genreMapper.toEntity(genreDto);
        GenreDao genreDao = genreDaoMapper.toDao(genre);
        Optional<GenreDao> optionalGenreDao = genreRepository.create(genreDao);
        GenreDao createdGenreDao = optionalGenreDao.orElseThrow(() ->
                new ServerException(messageSource.getMessage(SERVER_ERROR)));
        Genre created = genreDaoMapper.toEntity(createdGenreDao);
        return genreMapper.toDto(created);
    }

    @Override
    public List<GenreDto> getAll() {
        return genreRepository.getAll().stream()
                .map(genreDaoMapper::toEntity)
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    public List<GenreDto> getByBookId(Long id) {
        return genreRepository.getByBookId(id).stream()
                .map(genreDaoMapper::toEntity)
                .map(genreMapper::toDto)
                .toList();
    }

    @Override
    public GenreDto getById(Long id) {
        Optional<GenreDao> optionalGenreDao = genreRepository.getById(id);
        GenreDao genreDao = optionalGenreDao.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id})));
        Genre genre = genreDaoMapper.toEntity(genreDao);
        return genreMapper.toDto(genre);
    }

    @Override
    public void delete(Long id) {
        if (!genreRepository.delete(id)) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id}));
        }
    }

    @Override
    public void update(GenreDto genreDto) {
        Genre genre = genreMapper.toEntity(genreDto);
        GenreDao genreDao = genreDaoMapper.toDao(genre);
        Optional<GenreDao> optionalGenreDao = genreRepository.update(genreDao);
        if (optionalGenreDao.isEmpty()) {
            throw new ServerException(messageSource.getMessage(SERVER_ERROR));
        }
    }

    @Override
    public void addGenreToBook(Long bookId, GenreDto genreDto) {
        Genre genre = genreMapper.toEntity(genreDto);
        GenreDao genreDao = genreDaoMapper.toDao(genre);
        if (!genreRepository.setGenreToBook(bookId, genreDao)) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{bookId}));
        }
    }
}
