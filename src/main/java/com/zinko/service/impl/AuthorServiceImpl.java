package com.zinko.service.impl;

import com.zinko.data.AuthorRepository;
import com.zinko.data.dao.AuthorDao;
import com.zinko.model.Author;
import com.zinko.service.AuthorService;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.dto.AuthorDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.exception.ServerException;
import com.zinko.service.mapper.AuthorMapper;
import com.zinko.service.mapper.dao.impl.AuthorDaoMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    public static final String SERVER_ERROR = "server.error.message";
    public static final String NOT_FOUND_BOOK_WITH_ID_MESSAGE = "not.found.book.with.id.message";

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final AuthorDaoMapperImpl authorDaoMapper;
    private final CustomMessageSource messageSource;

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        AuthorDao authorDao = authorDaoMapper.toDao(author);
        Optional<AuthorDao> optionalAuthorDao = authorRepository.create(authorDao);
        AuthorDao createdAuthorDao = optionalAuthorDao.orElseThrow(() ->
                new ServerException(messageSource.getMessage(SERVER_ERROR)));
        Author createdAuthor = authorDaoMapper.toEntity(createdAuthorDao);
        return authorMapper.toDto(createdAuthor);
    }

    @Override
    public List<AuthorDto> getAll() {
        return authorRepository.getAll().stream()
                .map(authorDaoMapper::toEntity)
                .map(authorMapper::toDto)
                .toList();
    }

    @Override
    public AuthorDto getById(Long id) {
        Optional<AuthorDao> optionalAuthorDao = authorRepository.getById(id);
        AuthorDao authorDao = optionalAuthorDao.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id})));
        Author author = authorDaoMapper.toEntity(authorDao);
        return authorMapper.toDto(author);
    }

    @Override
    public void delete(Long id) {
        if (!authorRepository.delete(id)) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id}));
        }
    }

    @Override
    public void update(AuthorDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        AuthorDao authorDao = authorDaoMapper.toDao(author);
        Optional<AuthorDao> optionalAuthorDao = authorRepository.update(authorDao);
        if (optionalAuthorDao.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{authorDto.getId()}));
        }
    }
}
