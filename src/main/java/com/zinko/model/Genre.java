package com.zinko.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private Long id;
    private String name;
//    FANTASY, SCIENCE_FICTIONS, ACTION, DETECTIVE, THRILLER, ROMANCE, HORROR, ADVENTURES, COMEDY, DRAMA
}
