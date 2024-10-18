package com.zinko.servicebook.service.impl;

import com.zinko.servicebook.config.CustomMessageSource;
import com.zinko.servicebook.data.model.Series;
import com.zinko.servicebook.data.repository.SeriesRepository;
import com.zinko.servicebook.service.SeriesService;
import com.zinko.servicebook.service.dto.SeriesCreateDto;
import com.zinko.servicebook.service.dto.SeriesDto;
import com.zinko.servicebook.service.exception.NotFoundException;
import com.zinko.servicebook.service.mapper.SeriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public List<SeriesDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        return seriesRepository.findAll(pageRequest).stream()
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
