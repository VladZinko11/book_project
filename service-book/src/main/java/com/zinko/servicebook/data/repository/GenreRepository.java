package com.zinko.servicebook.data.repository;


import com.zinko.servicebook.data.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
