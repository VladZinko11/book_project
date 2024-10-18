package com.zinko.service.mapper;

import com.zinko.data.model.Series;
import com.zinko.service.dto.SeriesCreateDto;
import com.zinko.service.dto.SeriesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AuthorMapper.class, BookMapper.class})
public interface SeriesMapper {

    @Mapping(target = "books.author", ignore = true)
    @Mapping(target = "books.series", ignore = true)
    @Mapping(target = "author.series", ignore = true)
    @Mapping(target = "author.books", ignore = true)
    SeriesDto toDto(Series series);

    @Mapping(target = "books.author", ignore = true)
    @Mapping(target = "books.series", ignore = true)
    @Mapping(target = "author.series", ignore = true)
    @Mapping(target = "author.books", ignore = true)
    Series toEntity(SeriesDto seriesDto);

    Series toEntity(SeriesCreateDto seriesCreateDto);
}
