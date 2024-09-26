package com.zinko.service;

import com.zinko.service.dto.SeriesCreateDto;
import com.zinko.service.dto.SeriesDto;

import java.util.List;

public interface SeriesService {
    SeriesDto create(SeriesCreateDto seriesDto);

    List<SeriesDto> getAll();

    SeriesDto getById(Long id);

    void delete(Long id);

    void update(SeriesDto seriesDto);
}
