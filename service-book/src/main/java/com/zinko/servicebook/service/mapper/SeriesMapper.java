package com.zinko.servicebook.service.mapper;

import com.zinko.servicebook.data.model.Series;
import com.zinko.servicebook.service.dto.SeriesCreateDto;
import com.zinko.servicebook.service.dto.SeriesDto;
import com.zinko.servicebook.service.dto.SeriesSimpleDto;
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
