package com.zinko.data.impl;

import com.zinko.data.AuthorRepository;
import com.zinko.data.dao.AuthorDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    private static final String CREATE_AUTHOR = "INSERT INTO authors (first_name, last_name, biography) VALUES (:first_name, :last_name, :biography)";
    private static final String SELECT_AUTHOR_BY_ID = "SELECT id, first_name, last_name, biography FROM authors WHERE id=?";
    private static final String SELECT_ALL_AUTHORS = "SELECT id, first_name, last_name, biography FROM authors";
    private static final String UPDATE_AUTHOR = "UPDATE authors SET first_name=:first_name, last_name=:last_name, biography=:biography WHERE id=:id";
    private static final String DELETE_AUTHOR = "DELETE FROM authors WHERE id=?";

    private static final int COLUMN_INDEX_1 = 1;
    private static final int COLUMN_INDEX_2 = 2;
    private static final int COLUMN_INDEX_3 = 3;
    private static final int COLUMN_INDEX_4 = 4;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<AuthorDao> create(AuthorDao authorDao) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(getParamsFromBook(authorDao));
        namedParameterJdbcTemplate.update(CREATE_AUTHOR, sqlParameterSource, keyHolder);
        return getById((Long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));
    }

    @Override
    public Optional<AuthorDao> getById(Long id) {
        AuthorDao authorDao = jdbcTemplate.queryForObject(SELECT_AUTHOR_BY_ID, this::mapRow, id);
        return Optional.ofNullable(authorDao);
    }

    @Override
    public List<AuthorDao> getAll() {
        return jdbcTemplate.query(SELECT_ALL_AUTHORS, this::mapRow);
    }

    @Override
    public Optional<AuthorDao> update(AuthorDao authorDao) {
        int updated = namedParameterJdbcTemplate.update(UPDATE_AUTHOR, getParamsFromBook(authorDao));
        if (updated == 1) {
            return getById(authorDao.getId());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_AUTHOR, id) == 1;
    }

    private Map<String, Object> getParamsFromBook(AuthorDao authorDao) {
        Map<String, Object> params = new HashMap<>();
        if(authorDao.getId()!=null) {
            params.put("id", authorDao.getId());
        }
        params.put("first_name", authorDao.getFirstName());
        params.put("last_name", authorDao.getLastName());
        params.put("biography", authorDao.getBiography());
        return params;
    }

    private AuthorDao mapRow(ResultSet resultSet, int i) throws SQLException {
        AuthorDao authorDao = new AuthorDao();
        authorDao.setId(resultSet.getLong(COLUMN_INDEX_1));
        if (authorDao.getId() == null) {
            return null;
        }
        authorDao.setFirstName(resultSet.getString(COLUMN_INDEX_2));
        authorDao.setLastName(resultSet.getString(COLUMN_INDEX_3));
        authorDao.setBiography(resultSet.getString(COLUMN_INDEX_4));
        return authorDao;
    }
}
