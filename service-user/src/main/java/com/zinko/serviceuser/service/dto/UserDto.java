package com.zinko.serviceuser.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    Long id;
    String name;
    String email;
}