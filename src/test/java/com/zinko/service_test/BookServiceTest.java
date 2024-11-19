package com.zinko.service_test;

import com.zinko.config.CustomMessageSource;
import com.zinko.data.model.Book;
import com.zinko.data.repository.BookRepository;
import com.zinko.service.dto.BookDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.impl.BookServiceImpl;
import com.zinko.service.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    public static final String NOT_FOUND_BOOK_WITH_ID_MESSAGE = "not.found.book.with.id.message";
    @Mock
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private CustomMessageSource messageSource;
    private BookServiceImpl bookService;
    private BookDto bookDto;
    private Book savedBook;
    private BookDto savedBookDto;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, bookMapper, messageSource);

        bookDto = new BookDto();
        bookDto.setTitle("Effective Java");

        savedBook = bookMapper.toEntity(bookDto);
        savedBook.setId(1L);

        savedBookDto = bookMapper.toDto(savedBook);
    }

    @Test
    void createBookTest() {
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        assertEquals(savedBookDto, bookService.create(bookDto));

    }

    @Test
    void getByIdBookTest() {
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(savedBook));
        assertEquals(savedBookDto, bookService.getById(1L));
    }

    @Test
    void getByIdThrowExceptionTest() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()->bookService.getById(2L),  messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{2}));
    }

    @Test
    void updateBookTest() {
        savedBookDto.setPublicationDate(LocalDate.of(2008, 1, 1));
        savedBook = bookMapper.toEntity(savedBookDto);
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        assertEquals(bookService.update(savedBookDto), savedBookDto);
    }

    @Test
    void updateBookThrowExceptionTest() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, ()->bookService.update(savedBookDto), messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{1}));
    }
}
