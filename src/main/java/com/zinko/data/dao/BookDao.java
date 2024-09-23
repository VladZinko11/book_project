package com.zinko.data.dao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDao {
    private Long id;
    private String title;
    private Long authorId;
    private Long seriesId;
    private String description;
    private LocalDate publicationDate;
}
