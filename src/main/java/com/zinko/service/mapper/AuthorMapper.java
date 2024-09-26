package com.zinko.service.mapper;

import com.zinko.data.model.Author;
import com.zinko.service.dto.AuthorCreateDto;
import com.zinko.service.dto.AuthorDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {SeriesMapper.class, GenreMapper.class, BookMapper.class})
public interface AuthorMapper {

    @Mapping(target = "books.author", ignore = true)
    @Mapping(target = "books.series", ignore = true)
    @Mapping(target = "series.author", ignore = true)
    @Mapping(target = "series.books", ignore = true)
    AuthorDto toDto(Author author);

    @Mapping(target = "books.author", ignore = true)
    @Mapping(target = "books.series", ignore = true)
    @Mapping(target = "series.author", ignore = true)
    @Mapping(target = "series.books", ignore = true)
    Author toEntity(AuthorDto authorDto);

    Author toEntity(AuthorCreateDto authorCreateDto);

}
