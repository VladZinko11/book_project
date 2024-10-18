package com.zinko.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.zinko.data.model.Series}
 */
@Data
public class SeriesDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private AuthorDto author;
    private List<BookDto> books;
}