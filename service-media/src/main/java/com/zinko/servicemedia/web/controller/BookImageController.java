package com.zinko.servicemedia.web.controller;

import com.zinko.servicemedia.service.BookImageService;
import com.zinko.servicemedia.service.exception.ServerException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class BookImageController {
    private final BookImageService bookImageService;

    @GetMapping("books/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) {
        try {
            bookImageService.download(id, response);
        } catch (IOException e) {
            throw new ServerException();
        }
    }

    @PostMapping("books/{id}")
    public void upload(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        try {
            bookImageService.upload(id, file);
        } catch (IOException e) {
            throw new ServerException();
        }
    }
}
