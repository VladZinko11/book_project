package com.zinko.servicebook.service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate publicationDate;
    private AuthorSimpleDto author;
    private SeriesSimpleDto series;
    private List<GenreDto> genres;
    private String imageId;
}