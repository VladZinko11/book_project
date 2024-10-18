package com.zinko.servicebook.web.controller;

import com.zinko.servicebook.service.SeriesService;
import com.zinko.servicebook.service.dto.SeriesCreateDto;
import com.zinko.servicebook.service.dto.SeriesDto;
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
    public List<SeriesDto> getAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return seriesService.getAll(page, size);
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
