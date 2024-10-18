package com.zinko.servicebook.data.repository;


import com.zinko.servicebook.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
