package com.zinko.service.impl;

import com.zinko.data.BookRepository;
import com.zinko.model.Book;
import com.zinko.service.BookMapper;
import com.zinko.service.BookService;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.dto.BookDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.exception.ServerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    public static final String SERVER_ERROR = "server.error.message";
    public static final String NOT_FOUND_BOOK_WITH_ID_MESSAGE = "not.found.book.with.id.message";


    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CustomMessageSource messageSource;

    public BookServiceImpl(@Qualifier("bookRepositoryCacheableImpl") BookRepository bookRepository, BookMapper bookMapper, CustomMessageSource messageSource) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.messageSource = messageSource;
    }

    @Override
    public BookDto create(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        try {
            return bookMapper.toDto(bookRepository.create(book));
        } catch (IOException e) {
            throw new ServerException(messageSource.getMessage(SERVER_ERROR));
        }
    }

    @Override
    public List<BookDto> getAll() {
        try {
            return bookRepository.getAll().stream()
                    .map(bookMapper::toDto)
                    .toList();
        } catch (IOException e) {
            throw new ServerException(messageSource.getMessage(SERVER_ERROR));
        }
    }

    @Override
    public BookDto getById(Long id) {
        try {
            Optional<Book> optionalBook = bookRepository.getById(id);
            Book book = optionalBook.orElseThrow(() ->
                    new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id})));
            return bookMapper.toDto(book);
        } catch (IOException e) {
            throw new ServerException(messageSource.getMessage(SERVER_ERROR));
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (!bookRepository.deleteById(id)) {
                throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id}));
            }
        } catch (IOException e) {
            throw new ServerException(messageSource.getMessage(SERVER_ERROR));
        }
    }

    @Override
    public void update(BookDto bookDto) {
        StringUtils.hasText(bookDto.getDescription());
        try {
            Book book = bookRepository.getById(bookDto.getId())
                    .orElseThrow(() -> new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{bookDto.getId()})));
            if (StringUtils.hasText(bookDto.getAuthor())) {
                book.setAuthor(bookDto.getAuthor());
            }
            if (StringUtils.hasText(bookDto.getTitle())) {
                book.setTitle(bookDto.getTitle());
            }
            if (StringUtils.hasText(bookDto.getDescription())) {
                book.setDescription(bookDto.getDescription());
            }
            bookRepository.update(book);
        } catch (IOException e) {
            throw new ServerException(messageSource.getMessage(SERVER_ERROR));
        }
    }
}
