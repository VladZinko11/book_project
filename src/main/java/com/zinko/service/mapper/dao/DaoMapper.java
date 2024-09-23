package com.zinko.service.mapper.dao;

public interface DaoMapper<E, D> {
    E toEntity(D dao);

    D toDao(E entity);

}
