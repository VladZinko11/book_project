package com.zinko.web.controller;

import com.zinko.service.GenreService;
import com.zinko.service.dto.GenreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
public class GenreController {
    private final GenreService genreService;

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/")
    public GenreDto create(@RequestBody GenreDto genreDto) {
        return genreService.create(genreDto);
    }

    @GetMapping("/{id}")
    public GenreDto getById(@PathVariable Long id) {
        return genreService.getById(id);
    }

    @GetMapping("/")
    public List<GenreDto> getAll() {
        return genreService.getAll();
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/")
    public GenreDto update(@RequestBody GenreDto genreDto) {
        return genreService.update(genreDto);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        genreService.delete(id);
    }
}
