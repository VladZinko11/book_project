package com.zinko.service.dto;

import com.zinko.model.Author;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeriesDto {
    private Long id;
    private String title;
    private Author author;
    private String description;
}
