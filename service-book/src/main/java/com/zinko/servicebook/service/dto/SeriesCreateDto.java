package com.zinko.servicebook.service.dto;

import lombok.Data;

@Data
public class SeriesCreateDto {
    Long id;
    String title;
    AuthorSimpleDto author;
    String description;
}