package com.zinko.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BookDto {
    private Long id;
    private String title;
    private AuthorDto authorDto;
    private SeriesDto seriesDto;
    private String description;
    private List<GenreDto> genres;
}