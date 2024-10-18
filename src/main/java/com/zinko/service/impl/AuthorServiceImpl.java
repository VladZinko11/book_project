package com.zinko.service.impl;

import com.zinko.data.model.Author;
import com.zinko.data.repository.AuthorRepository;
import com.zinko.service.AuthorService;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.dto.AuthorCreateDto;
import com.zinko.service.dto.AuthorDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {
    public static final String NOT_FOUND_ENTITY_WITH_ID_MESSAGE = "not.found.entity.with.id.message";

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final CustomMessageSource messageSource;

    @Override
    public AuthorDto create(AuthorCreateDto authorCreateDto) {
        Author author = authorMapper.toEntity(authorCreateDto);
        authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    @Override
    public List<AuthorDto> getAll() {
        return authorRepository.getAll().stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @Override
    public AuthorDto getById(Long id) {
        Optional<Author> optionalAuthorDao = authorRepository.getById(id);
        Author author = optionalAuthorDao.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_ENTITY_WITH_ID_MESSAGE, new Object[]{id})));
        return authorMapper.toDto(author);
    }

    @Override
    public void delete(Long id) {
        if (!authorRepository.delete(id)) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_ENTITY_WITH_ID_MESSAGE, new Object[]{id}));
        }
    }

    @Override
    public void update(AuthorCreateDto authorCreateDto) {
        AuthorDto updatedAuthor = getById(authorCreateDto.getId());
        if (authorCreateDto.getFirstName() != null) {
            updatedAuthor.setFirstName(authorCreateDto.getFirstName());
        }
        if (authorCreateDto.getLastName() != null) {
            updatedAuthor.setLastName(authorCreateDto.getLastName());
        }
        if (authorCreateDto.getBiography() != null) {
            updatedAuthor.setBiography(authorCreateDto.getBiography());
        }
        Author author = authorMapper.toEntity(updatedAuthor);
        authorRepository.update(author);
    }
}
