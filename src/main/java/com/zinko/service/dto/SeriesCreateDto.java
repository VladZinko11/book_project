package com.zinko.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.zinko.data.model.Series}
 */
@Data
public class SeriesCreateDto implements Serializable {
    Long id;
    String title;
    AuthorSimpleDto author;
    String description;
}