package com.zinko.servicemedia.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-book")
public interface BooksServiceClient {

    @PostMapping("/api/v1/books/{bookId}/images/{imageId}")
    void addImage(@PathVariable Long bookId, @PathVariable String imageId);
}
