package com.zinko.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.zinko.data.model.Series}
 */
@Data
public class SeriesSimpleDto implements Serializable {
    private Long id;
    private String title;
    private String description;
}