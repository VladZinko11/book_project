package com.zinko.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.zinko.config.CustomMessageSource;
import com.zinko.data.model.Book;
import com.zinko.data.repository.BookRepository;
import com.zinko.service.BookImageService;
import com.zinko.service.exception.NotFoundException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookImageServiceImpl implements BookImageService {
    public static final String NOT_FOUND_BOOK_WITH_ID_MESSAGE = "not.found.book.with.id.message";
    private static final String NOT_FOUND_BOOK_IMAGE = "not.found.book.image.with.id.message";
    private final CustomMessageSource messageSource;
    private final GridFsTemplate gridFsTemplate;
    private final BookRepository bookRepository;

    @Override
    public void upload(Long bookId, MultipartFile file) throws IOException {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{bookId})));

        DBObject metaData = new BasicDBObject();
        metaData.put("id", bookId);
        InputStream inputStream = file.getInputStream();
        ObjectId imageId = gridFsTemplate.store(inputStream, file.getOriginalFilename(), file.getContentType(), metaData);

        book.setImageId(String.valueOf(imageId));
        bookRepository.save(book);
    }

    @Override
    public void download(Long bookId, HttpServletResponse response) throws IOException {
        if (bookRepository.existsById(bookId)) {
            GridFSFile filename = gridFsTemplate.findOne(new Query(Criteria.where("metadata.id").is(bookId)));
            if (filename != null) {
                response.setContentType("application/octet-stream");
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(filename.getFilename(), StandardCharsets.UTF_8).replace('+', ' ') + "\"");

                InputStream inputStream = gridFsTemplate.getResource(filename).getInputStream();
                byte[] buffer = new byte[8192];
                int bytesRead;
                ServletOutputStream outputStream = response.getOutputStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return;
            }
            throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_IMAGE, new Object[]{bookId}));
        }
        throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_WITH_ID_MESSAGE, new Object[]{bookId}));
    }
}
