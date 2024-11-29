package com.zinko.service;

import com.zinko.service.dto.SeriesCreateDto;
import com.zinko.service.dto.SeriesDto;

import java.util.List;

public interface SeriesService {
    SeriesDto create(SeriesCreateDto seriesDto);

    List<SeriesDto> getAll(int page, int size);

    SeriesDto getById(Long id);

    void delete(Long id);

    SeriesDto update(SeriesDto seriesDto);
}
