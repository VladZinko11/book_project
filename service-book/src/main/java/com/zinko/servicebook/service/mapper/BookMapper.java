package com.zinko.servicebook.service.mapper;

import com.zinko.servicebook.data.model.Book;
import com.zinko.servicebook.service.dto.BookDto;
import com.zinko.servicebook.service.dto.BookSimpleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {GenreMapper.class})
public interface BookMapper {

    BookDto toDto(Book book);

    @Mapping(target = "author.books", ignore = true)
    @Mapping(target = "author.series", ignore = true)
    @Mapping(target = "series.books", ignore = true)
    @Mapping(target = "series.author", ignore = true)
    Book toEntity(BookDto bookDto);

    Book toEntity(BookSimpleDto bookSimpleDto);

}
