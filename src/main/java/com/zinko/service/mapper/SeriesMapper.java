package com.zinko.service.mapper;

import com.zinko.model.Series;
import com.zinko.service.dto.SeriesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeriesMapper {
    SeriesDto toDto(Series series);

    Series toEntity(SeriesDto seriesDto);
}
