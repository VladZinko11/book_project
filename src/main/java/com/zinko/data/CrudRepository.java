package com.zinko.data;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<K, T> {
    Optional<T> create(T entity);

    Optional<T> getById(K key);

    List<T> getAll();

    Optional<T> update(T entity);

    boolean delete(K key);

}
