package com.zinko.service.impl;

import com.zinko.data.model.Book;
import com.zinko.data.repository.BookRepository;
import com.zinko.service.BookService;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.dto.BookDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    public static final String NOT_FOUND_BOOK_WITH_ID_MESSAGE = "not.found.entity.with.id.message";

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CustomMessageSource messageSource;

    @Override
    public BookDto create(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book saved = bookRepository.save(book);
        return bookMapper.toDto(saved);
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.getAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Optional<Book> optionalBookDao = bookRepository.getById(id);
        Book book = optionalBookDao.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id})));
        return bookMapper.toDto(book);
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.delete(id)) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id}));
        }
    }

    @Override
    public void update(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        bookRepository.update(book);
    }

}
