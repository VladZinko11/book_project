package com.zinko.service.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.zinko.data.model.Series}
 */
@Value
public class SeriesCreateDto implements Serializable {
    Long id;
    String title;
    AuthorDto author;
    String description;
}