package com.zinko.service.impl;

import com.zinko.data.BookRepository;
import com.zinko.data.dao.BookDao;
import com.zinko.model.Book;
import com.zinko.service.BookService;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.dto.BookDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.exception.ServerException;
import com.zinko.service.mapper.BookMapper;
import com.zinko.service.mapper.dao.impl.BookDaoMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    public static final String SERVER_ERROR = "server.error.message";
    public static final String NOT_FOUND_BOOK_WITH_ID_MESSAGE = "not.found.book.with.id.message";

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CustomMessageSource messageSource;
    private final BookDaoMapperImpl bookDaoMapper;

    @Override
    public BookDto create(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        BookDao bookDao = bookDaoMapper.toDao(book);
        Optional<BookDao> optionalBookDao = bookRepository.create(bookDao);
        BookDao createdBookDao = optionalBookDao.orElseThrow(() ->
                new ServerException(messageSource.getMessage(SERVER_ERROR)));
        Book createdBook = bookDaoMapper.toEntity(createdBookDao);
        return bookMapper.toDto(createdBook);
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.getAll().stream()
                .map(bookDaoMapper::toEntity)
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Optional<BookDao> optionalBookDao = bookRepository.getById(id);
        BookDao bookDao = optionalBookDao.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id})));
        Book book = bookDaoMapper.toEntity(bookDao);
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
        BookDao bookDao = bookDaoMapper.toDao(book);
        Optional<BookDao> optionalBookDao = bookRepository.update(bookDao);
        if (optionalBookDao.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{book.getId()}));
        }
    }

}
