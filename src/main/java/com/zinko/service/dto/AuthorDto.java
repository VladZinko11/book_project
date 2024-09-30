package com.zinko.service.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO for {@link com.zinko.data.model.Author}
 */
@Data
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
    private List<SeriesSimpleDto> series;
    private List<BookSimpleDto> books;
}