package com.zinko.service.impl;

import com.zinko.data.SeriesRepository;
import com.zinko.data.dao.SeriesDao;
import com.zinko.model.Series;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.SeriesService;
import com.zinko.service.dto.SeriesDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.exception.ServerException;
import com.zinko.service.mapper.SeriesMapper;
import com.zinko.service.mapper.dao.impl.SeriesDaoMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeriesServiceImpl implements SeriesService {
    public static final String SERVER_ERROR = "server.error.message";
    public static final String NOT_FOUND_BOOK_WITH_ID_MESSAGE = "not.found.book.with.id.message";

    private final SeriesRepository seriesRepository;
    private final SeriesMapper seriesMapper;
    private final SeriesDaoMapperImpl seriesDaoMapper;
    private final CustomMessageSource messageSource;

    @Override
    public SeriesDto create(SeriesDto seriesDto) {
        Series series = seriesMapper.toEntity(seriesDto);
        SeriesDao seriesDao = seriesDaoMapper.toDao(series);
        Optional<SeriesDao> optionalSeriesDao = seriesRepository.create(seriesDao);
        SeriesDao createdSeriesDao = optionalSeriesDao.orElseThrow(() ->
                new ServerException(messageSource.getMessage(SERVER_ERROR)));
        Series createdSeries = seriesDaoMapper.toEntity(createdSeriesDao);
        return seriesMapper.toDto(createdSeries);
    }

    @Override
    public List<SeriesDto> getAll() {
        return seriesRepository.getAll().stream()
                .map(seriesDaoMapper::toEntity)
                .map(seriesMapper::toDto)
                .toList();
    }

    @Override
    public SeriesDto getById(Long id) {
        Optional<SeriesDao> optionalSeriesDao = seriesRepository.getById(id);
        SeriesDao seriesDao = optionalSeriesDao.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id})));
        Series series = seriesDaoMapper.toEntity(seriesDao);
        return seriesMapper.toDto(series);
    }

    @Override
    public void delete(Long id) {
        if (!seriesRepository.delete(id)) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{id}));
        }
    }

    @Override
    public void update(SeriesDto seriesDto) {
        Series series = seriesMapper.toEntity(seriesDto);
        SeriesDao seriesDao = seriesDaoMapper.toDao(series);
        Optional<SeriesDao> optional = seriesRepository.update(seriesDao);
        if (optional.isEmpty()) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{series.getId()}));
        }
    }
}
