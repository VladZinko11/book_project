package com.zinko.servicebook.web.controller;

import com.zinko.servicebook.service.BookService;
import com.zinko.servicebook.service.dto.BookDto;
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
    public BookDto getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/")
    public List<BookDto> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        return bookService.getAll(page, size);
    }

    @PutMapping("/")
    public BookDto update(@RequestBody BookDto bookDto) {
        return bookService.update(bookDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PostMapping("/{bookId}/images/{imageId}")
    public void addImage(@PathVariable Long bookId, @PathVariable String imageId) {
        bookService.addImageId(bookId, imageId);
    }
}
