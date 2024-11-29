package com.zinko.servicebook.service;

import com.zinko.servicebook.service.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto create(BookDto bookDto);

    List<BookDto> getAll(int page, int size);

    BookDto getById(Long id);

    void delete(Long id);

    BookDto update(BookDto bookDto);

    void addImageId(Long bookId, String imageId);
}
