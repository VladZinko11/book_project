package com.zinko.service.mapper;

import com.zinko.model.Author;
import com.zinko.service.dto.AuthorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toDto(Author author);

    Author toEntity(AuthorDto authorDto);
}
