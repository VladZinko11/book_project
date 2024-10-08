package com.zinko.web.controller;

import com.zinko.service.SeriesService;
import com.zinko.service.dto.SeriesCreateDto;
import com.zinko.service.dto.SeriesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/series")
public class SeriesController {
    private final SeriesService seriesService;

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/")
    public SeriesDto create(@RequestBody SeriesCreateDto seriesCreateDto) {
        return seriesService.create(seriesCreateDto);
    }

    @GetMapping("/{id}")
    public SeriesDto getById(@PathVariable Long id) {
        return seriesService.getById(id);
    }

    @GetMapping("/")
    public List<SeriesDto> getAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return seriesService.getAll(page, size);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/")
    public SeriesDto update(@RequestBody SeriesDto seriesDto) {
        return seriesService.update(seriesDto);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        seriesService.delete(id);
    }
}
