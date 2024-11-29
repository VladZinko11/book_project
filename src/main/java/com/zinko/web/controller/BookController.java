package com.zinko.web.controller;

import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/")
    public BookDto create(@RequestBody BookDto bookDto) {
        return bookService.create(bookDto);
    }

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/")
    public List<BookDto> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        return bookService.getAll(page, size);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/")
    public BookDto update(@RequestBody BookDto bookDto) {
        return bookService.update(bookDto);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
