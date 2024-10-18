package com.zinko.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.zinko.data.model.Genre}
 */
@Data
public class GenreDto implements Serializable {
    private Long id;
    private String name;
}