package com.zinko.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.zinko.data.model.Book}
 */
@Data
public class BookDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private LocalDate publicationDate;
    private AuthorSimpleDto author;
    private SeriesSimpleDto series;
    private List<GenreDto> genres;
}