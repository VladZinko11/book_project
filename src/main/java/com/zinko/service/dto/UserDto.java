package com.zinko.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.zinko.data.model.User}
 */
@Data
public class UserDto implements Serializable {
    Long id;
    String name;
    String email;
}