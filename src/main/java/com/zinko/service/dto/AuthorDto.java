package com.zinko.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
}
