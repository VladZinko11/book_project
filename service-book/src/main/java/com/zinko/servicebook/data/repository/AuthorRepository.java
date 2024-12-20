package com.zinko.servicebook.data.repository;


import com.zinko.servicebook.data.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
