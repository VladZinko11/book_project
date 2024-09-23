package com.zinko.data.impl;

import com.zinko.data.BookRepository;
import com.zinko.data.dao.BookDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    public static final String CREATE_BOOK = "INSERT INTO books (title, author_id, series_id, publication_date, description) VALUES (:title, :author_id, :series_id, :publication_date, :description)";
    public static final String SELECT_BOOK_BY_ID = "SELECT id, title, author_id, series_id, publication_date, description FROM books WHERE id=?";
    public static final String SELECT_ALL_BOOKS = "SELECT id, title, author_id, series_id, publication_date, description FROM books";
    public static final String UPDATE_BOOK = "UPDATE books set title=:title, author_id=:author_id, series_id=:series_id, publication_date=:publication_date, description=:description WHERE id=:id";
    public static final String DELETE_BOOK = "DELETE FROM books WHERE id=?";

    private static final int COLUMN_INDEX_1 = 1;
    private static final int COLUMN_INDEX_2 = 2;
    private static final int COLUMN_INDEX_3 = 3;
    private static final int COLUMN_INDEX_4 = 4;
    private static final int COLUMN_INDEX_5 = 5;
    private static final int COLUMN_INDEX_6 = 6;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<BookDao> create(BookDao bookDao) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(getParamsFromBook(bookDao));
        namedParameterJdbcTemplate.update(CREATE_BOOK, sqlParameterSource, keyHolder);
        return getById((Long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));
    }

    @Override
    public Optional<BookDao> getById(Long id) {
        BookDao bookDao = jdbcTemplate.queryForObject(SELECT_BOOK_BY_ID, this::mapRow, id);
        return Optional.ofNullable(bookDao);
    }

    @Override
    public List<BookDao> getAll() {
        return jdbcTemplate.query(SELECT_ALL_BOOKS, this::mapRow);
    }

    @Override
    public Optional<BookDao> update(BookDao bookDao) {
        int updated = namedParameterJdbcTemplate.update(UPDATE_BOOK, getParamsFromBook(bookDao));
        if (updated == 1) {
            return getById(bookDao.getId());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_BOOK, id) == 1;
    }

    private BookDao mapRow(ResultSet resultSet, int i) throws SQLException {
        BookDao bookDao = new BookDao();
        bookDao.setId(resultSet.getLong(COLUMN_INDEX_1));
        if (bookDao.getId() == 0) {
            return null;
        }
        bookDao.setTitle(resultSet.getString(COLUMN_INDEX_2));
        bookDao.setAuthorId(resultSet.getLong(COLUMN_INDEX_3));
        bookDao.setSeriesId(resultSet.getLong(COLUMN_INDEX_4));
        bookDao.setPublicationDate(resultSet.getDate(COLUMN_INDEX_5).toLocalDate());
        bookDao.setDescription(resultSet.getString(COLUMN_INDEX_6));
        return bookDao;
    }

    private Map<String, Object> getParamsFromBook(BookDao bookDao) {
        Map<String, Object> params = new HashMap<>();
        if(bookDao.getId()!=null) {
            params.put("id", bookDao.getId());
        }
        params.put("title", bookDao.getTitle());
        params.put("author_id", bookDao.getAuthorId());
        params.put("series_id", bookDao.getSeriesId());
        params.put("publication_date", Date.valueOf(bookDao.getPublicationDate()));
        params.put("description", bookDao.getDescription());
        return params;
    }
}
