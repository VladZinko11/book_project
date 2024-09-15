package com.zinko.data.impl;

import com.zinko.data.BookRepository;
import com.zinko.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookRepositoryCacheableImpl implements BookRepository {
    private final BookRepositoryImpl bookRepository;
    private final HashMap<Long, Book> books = new HashMap<>();

    @Override
    public Book create(Book book) throws IOException {
        return bookRepository.create(book);
    }

    @Override
    public Optional<Book> getById(Long id) throws IOException {
        if(books.containsKey(id)) {
            return Optional.of(books.get(id));
        }
        else {
            Optional<Book> optionalBook = bookRepository.getById(id);
            optionalBook.ifPresent(book -> books.put(id, book));
            return optionalBook;
        }
    }

    @Override
    public List<Book> getAll() throws IOException {
        return bookRepository.getAll();
    }

    @Override
    public boolean deleteById(Long id) throws IOException {
        if(bookRepository.deleteById(id)) {
            books.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void update(Book book) throws IOException {
        bookRepository.update(book);
        books.remove(book.getId());
    }
}
