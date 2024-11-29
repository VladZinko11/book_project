package com.zinko.web.controller;

import com.zinko.service.BookImageService;
import com.zinko.service.exception.ServerException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books/{id}/images")
public class BookImageController {
    private final BookImageService bookImageService;

    @GetMapping("/")
    public void download(@PathVariable Long id, HttpServletResponse response) {
        try {
            bookImageService.download(id, response);
        } catch (IOException e) {
            throw new ServerException();
        }
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/")
    public void upload(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        try {
            bookImageService.upload(id, file);
        } catch (IOException e) {
            throw new ServerException();
        }
    }
}
