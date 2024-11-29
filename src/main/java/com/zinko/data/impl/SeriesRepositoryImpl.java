package com.zinko.data.impl;

import com.zinko.data.SeriesRepository;
import com.zinko.data.dao.SeriesDao;
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
public class SeriesRepositoryImpl implements SeriesRepository {

    private static final String CREATE_SERIES = "INSERT INTO series (title, description, author_id) VALUES (:title, :description, :author_id)";
    private static final String SELECT_SERIES_BY_ID = "SELECT id, title, description, author_id FROM series WHERE id=?";
    private static final String SELECT_ALL_SERIES = "SELECT id, title, description, author_id FROM series";
    private static final String UPDATE_SERIES = "UPDATE series SET title=:title, description=:description, author_id=:author_id WHERE id=:id";
    private static final String DELETE_SERIES = "DELETE FROM series WHERE id=?";

    private static final int COLUMN_INDEX_1 = 1;
    private static final int COLUMN_INDEX_2 = 2;
    private static final int COLUMN_INDEX_3 = 3;
    private static final int COLUMN_INDEX_4 = 4;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Optional<SeriesDao> create(SeriesDao seriesDao) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(getParamsFromBook(seriesDao));
        namedParameterJdbcTemplate.update(CREATE_SERIES, sqlParameterSource, keyHolder);
        return getById((Long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));
    }

    @Override
    public Optional<SeriesDao> getById(Long id) {
        SeriesDao seriesDao = jdbcTemplate.queryForObject(SELECT_SERIES_BY_ID, this::mapRow, id);
        return Optional.ofNullable(seriesDao);
    }

    @Override
    public List<SeriesDao> getAll() {
        return jdbcTemplate.query(SELECT_ALL_SERIES, this::mapRow);
    }

    @Override
    public Optional<SeriesDao> update(SeriesDao seriesDao) {
        int updated = namedParameterJdbcTemplate.update(UPDATE_SERIES,getParamsFromBook(seriesDao));
        if (updated == 1) {
            return getById(seriesDao.getId());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_SERIES, id) == 1;
    }

    private Map<String, Object> getParamsFromBook(SeriesDao seriesDao) {
        Map<String, Object> params = new HashMap<>();
        if(seriesDao.getId()!=null) {
            params.put("id", seriesDao.getId());
        }
        params.put("title", seriesDao.getTitle());
        params.put("author_id", seriesDao.getAuthorId());
        params.put("description", seriesDao.getDescription());
        return params;
    }

    private SeriesDao mapRow(ResultSet resultSet, int i) throws SQLException {
        SeriesDao seriesDao = new SeriesDao();
        seriesDao.setId(resultSet.getLong(COLUMN_INDEX_1));
        seriesDao.setTitle(resultSet.getString(COLUMN_INDEX_2));
        seriesDao.setDescription(resultSet.getString(COLUMN_INDEX_3));
        seriesDao.setAuthorId(resultSet.getLong(COLUMN_INDEX_4));
        return seriesDao;
    }
}
