package com.zinko.servicebook.service.dto;

import lombok.Data;

@Data
public class AuthorSimpleDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
}