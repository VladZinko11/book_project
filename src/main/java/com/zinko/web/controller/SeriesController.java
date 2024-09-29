package com.zinko.web.controller;

import com.zinko.service.SeriesService;
import com.zinko.service.dto.SeriesCreateDto;
import com.zinko.service.dto.SeriesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/series")
public class SeriesController {
    private final SeriesService seriesService;

    @PostMapping("/")
    public SeriesDto create(@RequestBody SeriesCreateDto seriesCreateDto) {
        return seriesService.create(seriesCreateDto);
    }

    @GetMapping("/{id}")
    public SeriesDto getById(@PathVariable Long id) {
        return seriesService.getById(id);
    }

    @GetMapping("/")
    public List<SeriesDto> getAll() {
        return seriesService.getAll();
    }

    @PutMapping("/")
    public SeriesDto update(@RequestBody SeriesDto seriesDto) {
        return seriesService.update(seriesDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        seriesService.delete(id);
    }
}
