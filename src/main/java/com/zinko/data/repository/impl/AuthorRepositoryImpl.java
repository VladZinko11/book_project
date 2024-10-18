package com.zinko.data.repository.impl;

import com.zinko.data.model.Author;
import com.zinko.data.repository.AuthorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    public static final String DELETE_BY_ID = "DELETE FROM Author WHERE id=:id";
    public static final String FROM_AUTHOR = "FROM Author";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Author save(Author entity) {
        manager.persist(entity);
        return entity;
    }

    @Override
    public Optional<Author> getById(Long key) {
        Author author = manager.find(Author.class, key);
        return Optional.ofNullable(author);
    }

    @Override
    @BatchSize(size = 20)
    public List<Author> getAll() {
        return manager.createQuery(FROM_AUTHOR, Author.class)
                .getResultList();
    }

    @Override
    public Author update(Author entity) {
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
