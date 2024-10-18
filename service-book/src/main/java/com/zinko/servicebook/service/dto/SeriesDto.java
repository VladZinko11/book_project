package com.zinko.servicebook.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class SeriesDto {
    private Long id;
    private String title;
    private String description;
    private AuthorSimpleDto author;
    private List<BookSimpleDto> books;
}