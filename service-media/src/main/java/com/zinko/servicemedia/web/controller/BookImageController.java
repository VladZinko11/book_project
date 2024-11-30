package com.zinko.servicemedia.web.controller;

import com.zinko.servicemedia.service.BookImageService;
import com.zinko.servicemedia.service.exception.ServerException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class BookImageController {
    private final BookImageService bookImageService;

    @GetMapping("books/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) {
        try {
            String fileName = bookImageService.download(id, response);
            response.setContentType("application/octet-stream");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace('+', ' ') + "\"");
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
