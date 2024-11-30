package com.zinko.servicemedia.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.zinko.servicemedia.config.CustomMessageSource;
import com.zinko.servicemedia.service.BookImageService;
import com.zinko.servicemedia.service.BooksServiceClient;
import com.zinko.servicemedia.service.exception.NotFoundException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class BookImageServiceImpl implements BookImageService {
    private static final String NOT_FOUND_BOOK_IMAGE = "not.found.book.image.with.id.message";
    private final CustomMessageSource messageSource;
    private final GridFsTemplate gridFsTemplate;
    private final BooksServiceClient booksServiceClient;

    @Transactional
    @Override
    public void upload(Long bookId, MultipartFile file) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("id", bookId);
        InputStream inputStream = file.getInputStream();
        ObjectId imageId = gridFsTemplate.store(inputStream, file.getOriginalFilename(), file.getContentType(), metaData);
        booksServiceClient.addImage(bookId, String.valueOf(imageId));
    }

    @Override
    public String download(Long bookId, HttpServletResponse response) throws IOException {
        GridFSFile filename = gridFsTemplate.findOne(new Query(Criteria.where("metadata.id").is(bookId)));
        if (filename != null) {

            InputStream inputStream = gridFsTemplate.getResource(filename).getInputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            ServletOutputStream outputStream = response.getOutputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return filename.getFilename();
        }
        throw new NotFoundException(messageSource.getMessage(NOT_FOUND_BOOK_IMAGE, new Object[]{bookId}));
    }
}
