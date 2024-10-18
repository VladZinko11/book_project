package com.zinko.servicebook.service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookSimpleDto {
    private Long id;
    private String title;
    private String description;
    private List<GenreDto> genres;
    private LocalDate publicationDate;
}