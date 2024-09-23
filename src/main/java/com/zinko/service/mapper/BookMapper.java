package com.zinko.service.mapper;

import com.zinko.model.Book;
import com.zinko.service.dto.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);
}
