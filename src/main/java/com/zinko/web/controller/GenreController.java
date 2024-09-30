package com.zinko.web.controller;

import com.zinko.service.GenreService;
import com.zinko.service.dto.GenreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
public class GenreController {
    private final GenreService genreService;

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

    @PutMapping("/")
    public GenreDto update(@RequestBody GenreDto genreDto) {
        return genreService.update(genreDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        genreService.delete(id);
    }
}
