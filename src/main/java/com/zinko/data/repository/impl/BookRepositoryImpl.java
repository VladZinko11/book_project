package com.zinko.data.repository.impl;

import com.zinko.data.model.Book;
import com.zinko.data.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    public static final String FROM_BOOK = "FROM Book";
    public static final String DELETE_BY_ID = "DELETE FROM Book WHERE id=:id";

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Book save(Book entity) {
        manager.persist(entity);
        return entity;
    }

    @Override
    public Optional<Book> getById(Long key) {
        Book book = manager.find(Book.class, key);
        return Optional.ofNullable(book);
    }

    @Override
    @BatchSize(size = 20)
    public List<Book> getAll() {
        return manager.createQuery(FROM_BOOK, Book.class)
                .getResultList();
    }

    @Override
    public Book update(Book entity) {
        return manager.merge(entity);
    }

    @Override
    public boolean delete(Long key) {
        int deleted = manager.createQuery(DELETE_BY_ID)
                .setParameter("id", key)
                .executeUpdate();
        return deleted == 1;
    }
}
