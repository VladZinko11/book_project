package com.zinko.data.repository.impl;

import com.zinko.data.model.Genre;
import com.zinko.data.repository.GenreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {

    public static final String DELETE_BY_ID = "DELETE FROM Genre WHERE id=:id";
    public static final String FROM_GENRE = "FROM Genre";
    public static final String BY_BOOK_ID = "FROM Genre WHERE Book.id=:book_id";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Genre save(Genre entity) {
        manager.persist(entity);
        return entity;
    }

    @Override
    public Optional<Genre> getById(Long key) {
        Genre genre = manager.find(Genre.class, key);
        return Optional.ofNullable(genre);
    }

    @Override
    @BatchSize(size = 20)
    public List<Genre> getAll() {
        return manager.createQuery(FROM_GENRE, Genre.class)
                .getResultList();
    }

    @Override
    public Genre update(Genre entity) {
        return manager.merge(entity);
    }

    @Override
    public boolean delete(Long key) {
        int deleted = manager.createQuery(DELETE_BY_ID)
                .setParameter("id", key)
                .executeUpdate();
        return deleted == 1;
    }

    @Override
    public List<Genre> getByBookId(Long bookId) {
        return manager.createQuery(BY_BOOK_ID, Genre.class)
                .setParameter("book_id", bookId)
                .getResultList();
    }

}
