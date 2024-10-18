package com.zinko.service.mapper.dao.impl;

import com.zinko.data.AuthorRepository;
import com.zinko.data.dao.AuthorDao;
import com.zinko.data.dao.SeriesDao;
import com.zinko.model.Author;
import com.zinko.model.Series;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.exception.ServerException;
import com.zinko.service.mapper.dao.SeriesDaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SeriesDaoMapperImpl implements SeriesDaoMapper {
    public static final String SERVER_ERROR = "server.error.message";

    private final AuthorRepository authorRepository;
    private final AuthorDaoMapperImpl authorDaoMapper;
    private final CustomMessageSource messageSource;

    @Override
    public Series toEntity(SeriesDao seriesDao) {
        Series series = new Series();
        Long authorId = seriesDao.getAuthorId();
        Optional<AuthorDao> optionalAuthorDao = authorRepository.getById(authorId);
        AuthorDao authorDao = optionalAuthorDao.orElseThrow(() -> new ServerException(messageSource.getMessage(SERVER_ERROR)));
        Author author = authorDaoMapper.toEntity(authorDao);
        series.setId(seriesDao.getId());
        series.setTitle(seriesDao.getTitle());
        series.setAuthor(author);
        series.setDescription(seriesDao.getDescription());
        return series;
    }

    @Override
    public SeriesDao toDao(Series series) {
        SeriesDao seriesDao = new SeriesDao();
        if (series.getId() != null) {
            seriesDao.setId(series.getId());
        }
        seriesDao.setTitle(series.getTitle());
        seriesDao.setAuthorId(series.getAuthor().getId());
        seriesDao.setDescription(series.getDescription());
        return seriesDao;
    }
}
