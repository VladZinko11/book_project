package com.zinko.servicebook.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
    private List<SeriesSimpleDto> series;
    private List<BookSimpleDto> books;
}