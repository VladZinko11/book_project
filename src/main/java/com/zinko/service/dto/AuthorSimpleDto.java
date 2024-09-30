package com.zinko.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.zinko.data.model.Author}
 */
@Data
public class AuthorSimpleDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
}