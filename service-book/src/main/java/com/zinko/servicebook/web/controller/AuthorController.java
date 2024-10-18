package com.zinko.servicebook.web.controller;

import com.zinko.servicebook.service.AuthorService;
import com.zinko.servicebook.service.dto.AuthorDto;
import com.zinko.servicebook.service.dto.AuthorSimpleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/")
    public AuthorDto create(@RequestBody AuthorSimpleDto authorDto) {
        return authorService.create(authorDto);
    }

    @GetMapping("/{id}")
    public AuthorDto getById(@PathVariable Long id) {
        return authorService.getById(id);
    }

    @GetMapping("/")
    public List<AuthorSimpleDto> getAll(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return authorService.getAll(page, size);
    }

    @PutMapping("/")
    public AuthorDto update(@RequestBody AuthorDto authorDto) {
        return authorService.update(authorDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}
