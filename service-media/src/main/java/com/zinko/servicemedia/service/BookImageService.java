package com.zinko.servicemedia.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookImageService {

    void upload(Long bookId, MultipartFile file) throws IOException;

    String download(Long bookId, HttpServletResponse response) throws IOException;
}
