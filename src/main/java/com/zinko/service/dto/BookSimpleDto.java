package com.zinko.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.zinko.data.model.Book}
 */
@Data
public class BookSimpleDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private List<GenreDto> genres;
    private LocalDate publicationDate;
}