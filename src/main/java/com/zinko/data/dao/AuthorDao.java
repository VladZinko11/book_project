package com.zinko.data.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDao {
    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
}
