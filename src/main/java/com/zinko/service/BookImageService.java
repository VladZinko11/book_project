package com.zinko.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookImageService {

    void upload(Long bookId, MultipartFile file) throws IOException;

    void download(Long bookId, HttpServletResponse response) throws IOException;
}
