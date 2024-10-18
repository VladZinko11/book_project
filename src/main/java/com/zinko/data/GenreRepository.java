package com.zinko.data;

import com.zinko.data.dao.GenreDao;

import java.util.List;

public interface GenreRepository extends CrudRepository<Long, GenreDao> {
    List<GenreDao> getByBookId(Long bookId);

    boolean setGenreToBook(Long bookId, GenreDao genreDao);

}
