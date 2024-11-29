package com.zinko.data.impl;

import com.zinko.data.GenreRepository;
import com.zinko.data.dao.GenreDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {

    public static final String SELECT_GENRES_BY_BOOK_ID = "SELECT id, name FROM genres g JOIN books_genres bg ON g.id = bg.genre_id WHERE bg.book_id=?";
    public static final String ADD_GENRE_TO_BOOK = "INSERT INTO books_genres (book_id, genre_id) VALUES (?, ?)";
    public static final String CREATE_GENRE = "INSERT INTO genres (id, name) VALUES (?, ?)";
    public static final String SELECT_GENRE_BY_ID = "SELECT id, name FROM genres WHERE id=?";
    public static final String DELETE_GENRE_BY_ID = "DELETE FROM genres WHERE id=?";
    public static final String SELECT_ALL_GENRES = "SELECT id, name FROM genres";
    public static final String UPDATE_GENRE = "UPDATE genres SET name=? WHERE id=?";

    private static final int COLUMN_INDEX_1 = 1;
    private static final int COLUMN_INDEX_2 = 2;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<GenreDao> getByBookId(Long bookId) {
        return jdbcTemplate.query(SELECT_GENRES_BY_BOOK_ID, this::mapRow, bookId);
    }

    @Override
    public boolean setGenreToBook(Long bookId, GenreDao genreDao) {
        int updated = jdbcTemplate.update(ADD_GENRE_TO_BOOK, bookId, genreDao.getId());
        return updated == 1;
    }

    @Override
    public Optional<GenreDao> create(GenreDao genreDao) {
        int created = jdbcTemplate.update(CREATE_GENRE, genreDao.getId(), genreDao.getName());
        if (created == 1) {
            return getById(genreDao.getId());
        }
        return Optional.empty();
    }

    @Override
    public Optional<GenreDao> getById(Long id) {
        GenreDao genreDao = jdbcTemplate.queryForObject(SELECT_GENRE_BY_ID, this::mapRow, id);
        return Optional.ofNullable(genreDao);
    }

    @Override
    public List<GenreDao> getAll() {
        return jdbcTemplate.query(SELECT_ALL_GENRES, this::mapRow);
    }

    @Override
    public Optional<GenreDao> update(GenreDao genreDao) {
        if (jdbcTemplate.update(UPDATE_GENRE, genreDao.getName(), genreDao.getId()) == 1) {
            return getById(genreDao.getId());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_GENRE_BY_ID, id) == 1;
    }

    private GenreDao mapRow(ResultSet resultSet, int i) throws SQLException {
        GenreDao genreDao = new GenreDao();
        genreDao.setId(resultSet.getLong(COLUMN_INDEX_1));
        if (genreDao.getId() == null) {
            return null;
        }
        genreDao.setName(resultSet.getString(COLUMN_INDEX_2));
        return genreDao;
    }
}
