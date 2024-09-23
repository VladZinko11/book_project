package com.zinko.service.mapper.dao.impl;

import com.zinko.data.dao.GenreDao;
import com.zinko.model.Genre;
import com.zinko.service.mapper.dao.GenreDaoMapper;
import org.springframework.stereotype.Component;

@Component
public class GenreDaoMapperImpl implements GenreDaoMapper {
    @Override
    public Genre toEntity(GenreDao genreDao) {
        Genre genre = new Genre();
        genre.setId(genreDao.getId());
        genre.setName(genreDao.getName());
        return genre;
    }

    @Override
    public GenreDao toDao(Genre genre) {
        GenreDao genreDao = new GenreDao();
        genreDao.setId(genre.getId());
        genreDao.setName(genre.getName());
        return genreDao;
    }
}
