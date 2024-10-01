package com.zinko.data.cache;

import com.zinko.model.Book;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class BookRepositoryGetByIdCache implements MyCache<Long, Book>{
    private final HashMap<Long, Book> books = new HashMap<>();

    @Override
    public boolean searchCache(Long id) {
        return books.containsKey(id);
    }

    @Override
    public Optional<Book> getFromCache(Long id) {
        return Optional.of(books.get(id));
    }

    @Override
    public void addToCache(Long id, Book book) {
        books.put(id, book);
    }
}
