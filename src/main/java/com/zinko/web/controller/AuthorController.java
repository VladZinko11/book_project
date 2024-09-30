package com.zinko.web.controller;

import com.zinko.service.AuthorService;
import com.zinko.service.dto.AuthorDto;
import com.zinko.service.dto.AuthorSimpleDto;
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
    public List<AuthorSimpleDto> getAll() {
        return authorService.getAll();
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
