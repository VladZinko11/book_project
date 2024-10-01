package com.zinko.data.cache;


import java.util.Optional;

public interface MyCache<K, T> {

    boolean searchCache(K k);

    Optional<T> getFromCache(K k);

    void addToCache(K k, T t);
}
