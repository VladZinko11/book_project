package com.zinko.service.mapper;

import com.zinko.data.model.Genre;
import com.zinko.service.dto.GenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenreMapper {
    GenreDto toDto(Genre genre);

    Genre toEntity(GenreDto genreDto);

    List<GenreDto> toDto(List<Genre> genres);

    List<Genre> toEntity(List<GenreDto> genresDto);

}
