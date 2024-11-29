package com.zinko.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.zinko.data.model.Author}
 */
@Data
public class AuthorDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
    private List<SeriesDto> series;
    private List<BookDto> books;
}