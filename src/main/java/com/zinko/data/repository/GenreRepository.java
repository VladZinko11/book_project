package com.zinko.data.repository;


import com.zinko.data.model.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Long, Genre> {
    List<Genre> getByBookId(Long bookId);


}
