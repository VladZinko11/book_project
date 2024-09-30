package com.zinko.service.mapper;

import com.zinko.data.model.Series;
import com.zinko.service.dto.SeriesCreateDto;
import com.zinko.service.dto.SeriesDto;
import com.zinko.service.dto.SeriesSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AuthorMapper.class})
public interface SeriesMapper {

    SeriesDto toDto(Series series);

    @Mapping(target = "books.author", ignore = true)
    @Mapping(target = "books.series", ignore = true)
    @Mapping(target = "author.series", ignore = true)
    @Mapping(target = "author.books", ignore = true)
    Series toEntity(SeriesDto seriesDto);

    Series toEntity(SeriesSimpleDto seriesSimpleDto);

    Series toEntity(SeriesCreateDto seriesCreateDto);

}
