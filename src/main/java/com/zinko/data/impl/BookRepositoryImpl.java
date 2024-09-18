package com.zinko.data.impl;


import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.zinko.data.BookRepository;
import com.zinko.data.IdGenerator;
import com.zinko.model.Book;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private static final String CSV_FILE = "src/main/resources/books.csv";
    private final ObjectWriter bookWriter;
    private final ObjectReader bookReader;
    private final IdGenerator idGenerator;
    private final CustomMessageSource messageSource;

    @Override
    public Book create(Book book) throws IOException {
        List<Book> books = readBooks();
        book.setId(idGenerator.generateBookId(books));
        books.add(book);
        writeBooks(books);
        return book;
    }

    @Override
    public Optional<Book> getById(Long id) throws IOException {
        return readBooks().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Book> getAll() throws IOException {
        return readBooks();
    }

    @Override
    public boolean deleteById(Long id) throws IOException {
        List<Book> books = readBooks();
        boolean removed = books.removeIf(b -> Objects.equals(b.getId(), id));
        writeBooks(books);
        return removed;
    }

    @Override
    public void update(Book book) throws IOException {
        List<Book> books = readBooks();
        Book updated = books.stream()
                .filter(b -> b.getId().equals(book.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("not.found.book.with.id.message", new Object[]{book.getId()})));
        int index = books.indexOf(updated);
        books.set(index, book);
        writeBooks(books);
    }


    private List<Book> readBooks() throws IOException {
        MappingIterator<Book> iterator = bookReader.readValues(new FileReader(CSV_FILE));
        return iterator.readAll();
    }

    private void writeBooks(List<Book> books) throws IOException {
        bookWriter.writeValue(new FileWriter(CSV_FILE), books);
    }
}