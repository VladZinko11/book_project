package com.zinko.service.impl;

import com.zinko.data.BookRepository;
import com.zinko.model.Book;
import com.zinko.service.BookMapper;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto create(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        try {
            return bookMapper.toDto(bookRepository.create(book));
        } catch (IOException e) {
            throw new ServerException("Server error");
        }
    }

    @Override
    public List<BookDto> getAll() {
        try {
            return bookRepository.getAll().stream()
                    .map(bookMapper::toDto)
                    .toList();
        } catch (IOException e) {
            throw new ServerException("Server error");
        }
    }

    @Override
    public BookDto get(Long id) {
        try {
            Optional<Book> optionalBook = bookRepository.getById(id);
            Book book = optionalBook.orElseThrow(() -> new NotFoundException("Not found book with id: " + id));
            return bookMapper.toDto(book);
        } catch (IOException e) {
            throw new ServerException("Server error");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (!bookRepository.deleteById(id)) {
                throw new NotFoundException("Not found book with id: " + id);
            }
        } catch (IOException e) {
            throw new ServerException("Server error");
        }
    }

    @Override
    public void update(BookDto bookDto) {
        StringUtils.hasText(bookDto.getDescription());
        try {
            Book book = bookRepository.getById(bookDto.getId()).orElseThrow(() -> new NotFoundException("Not found book with id" + bookDto.getId()));
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
            throw new ServerException("Server error");
        }
    }
}
