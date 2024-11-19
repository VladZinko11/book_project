package com.zinko.web.controller;

import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @PostMapping("/")
    public BookDto create(@RequestBody BookDto bookDto) {
        return bookService.create(bookDto);
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable("id") Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/")
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @PutMapping("/")
    public BookDto update(@RequestBody BookDto bookDto) {
        return bookService.update(bookDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookService.delete(id);
    }
}
