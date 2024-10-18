package com.zinko.data.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<K, T> {
    T save(T entity);

    Optional<T> getById(K key);

    List<T> getAll();

    T update(T entity);

    boolean delete(K key);

}
