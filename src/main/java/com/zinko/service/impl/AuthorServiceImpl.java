package com.zinko.service.impl;

import com.zinko.config.CustomMessageSource;
import com.zinko.data.model.Author;
import com.zinko.data.repository.AuthorRepository;
import com.zinko.service.AuthorService;
import com.zinko.service.dto.AuthorDto;
import com.zinko.service.dto.AuthorSimpleDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {
    public static final String NOT_FOUND_AUTHOR_WITH_ID_MESSAGE = "not.found.author.with.id.message";

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final CustomMessageSource messageSource;

    @Override
    public AuthorDto create(AuthorSimpleDto authorDto) {
        Author author = authorMapper.toEntity(authorDto);
        authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    @Override
    public List<AuthorSimpleDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        return authorRepository.findAll(pageRequest).stream()
                .map(authorMapper::toSimpleDto)
                .toList();
    }

    @Override
    public AuthorDto getById(Long id) {
        Optional<Author> optionalAuthorDao = authorRepository.findById(id);
        Author author = optionalAuthorDao.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_AUTHOR_WITH_ID_MESSAGE, new Object[]{id})));
        return authorMapper.toDto(author);
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        if (authorRepository.existsById(authorDto.getId())) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_AUTHOR_WITH_ID_MESSAGE, new Object[]{authorDto.getId()}));
        }
        Author author = authorMapper.toEntity(authorDto);
        Author saved = authorRepository.save(author);
        return authorMapper.toDto(saved);
    }
}
