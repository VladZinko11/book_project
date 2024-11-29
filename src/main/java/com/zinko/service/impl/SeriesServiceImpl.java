package com.zinko.service.impl;

import com.zinko.config.CustomMessageSource;
import com.zinko.data.model.Series;
import com.zinko.data.repository.SeriesRepository;
import com.zinko.service.SeriesService;
import com.zinko.service.dto.SeriesCreateDto;
import com.zinko.service.dto.SeriesDto;
import com.zinko.service.exception.NotFoundException;
import com.zinko.service.mapper.SeriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeriesServiceImpl implements SeriesService {
    public static final String NOT_FOUND_SERIES_WITH_ID_MESSAGE = "not.found.series.with.id.message";

    private final SeriesRepository seriesRepository;
    private final SeriesMapper seriesMapper;
    private final CustomMessageSource messageSource;

    @Override
    public SeriesDto create(SeriesCreateDto seriesDto) {
        Series series = seriesMapper.toEntity(seriesDto);
        Series saved = seriesRepository.save(series);
        return seriesMapper.toDto(saved);
    }

    @Override
    public List<SeriesDto> getAll() {
        return seriesRepository.findAll().stream()
                .map(seriesMapper::toDto)
                .toList();
    }

    @Override
    public SeriesDto getById(Long id) {
        Series series = seriesRepository.findById(id).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage(NOT_FOUND_SERIES_WITH_ID_MESSAGE, new Object[]{id})));
        return seriesMapper.toDto(series);
    }

    @Override
    public void delete(Long id) {
        seriesRepository.deleteById(id);
    }

    @Override
    public SeriesDto update(SeriesDto seriesDto) {
        if (!seriesRepository.existsById(seriesDto.getId())) {
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_SERIES_WITH_ID_MESSAGE, new Object[]{seriesDto.getId()}));
        }
        Series series = seriesMapper.toEntity(seriesDto);
        Series saved = seriesRepository.save(series);
        return seriesMapper.toDto(saved);
    }
}
