package com.zinko.servicebook.service.mapper;

import com.zinko.servicebook.data.model.Author;
import com.zinko.servicebook.service.dto.AuthorDto;
import com.zinko.servicebook.service.dto.AuthorSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    @Mapping(target = "books.author", ignore = true)
    @Mapping(target = "books.series", ignore = true)
    @Mapping(target = "series.author", ignore = true)
    @Mapping(target = "series.books", ignore = true)
    Author toEntity(AuthorDto authorDto);

    Author toEntity(AuthorSimpleDto authorSimpleDto);

    AuthorSimpleDto toSimpleDto(Author author);

}
