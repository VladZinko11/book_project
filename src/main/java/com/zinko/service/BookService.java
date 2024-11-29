package com.zinko.service;

import com.zinko.service.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto create(BookDto bookDto);

    List<BookDto> getAll();

    BookDto getById(Long id);

    void delete(Long id);

    BookDto update(BookDto bookDto);

}
