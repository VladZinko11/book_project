package com.zinko.data;

import com.zinko.model.Book;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book create(Book book) throws IOException;

    Optional<Book> getById(Long id) throws IOException;

    List<Book> getAll() throws IOException;

    boolean deleteById(Long id) throws IOException;

    void update(Book book) throws IOException;
}
