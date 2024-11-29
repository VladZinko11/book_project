package com.zinko.service.mapper;

import com.zinko.model.Genre;
import com.zinko.service.dto.GenreDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto toDto(Genre genre);

    Genre toEntity(GenreDto genreDto);
}
