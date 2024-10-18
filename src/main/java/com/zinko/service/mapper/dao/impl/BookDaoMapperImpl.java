package com.zinko.service.mapper.dao.impl;

import com.zinko.data.AuthorRepository;
import com.zinko.data.GenreRepository;
import com.zinko.data.SeriesRepository;
import com.zinko.data.dao.AuthorDao;
import com.zinko.data.dao.BookDao;
import com.zinko.data.dao.GenreDao;
import com.zinko.data.dao.SeriesDao;
import com.zinko.model.Author;
import com.zinko.model.Book;
import com.zinko.model.Genre;
import com.zinko.model.Series;
import com.zinko.service.CustomMessageSource;
import com.zinko.service.exception.ServerException;
import com.zinko.service.mapper.dao.BookDaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookDaoMapperImpl implements BookDaoMapper {
    public static final String SERVER_ERROR = "server.error.message";

    private final CustomMessageSource messageSource;
    private final AuthorRepository authorRepository;
    private final SeriesRepository seriesRepository;
    private final AuthorDaoMapperImpl authorDaoMapper;
    private final SeriesDaoMapperImpl seriesDaoMapper;
    private final GenreRepository genreRepository;
    private final GenreDaoMapperImpl genreDaoMapper;

    @Override
    public Book toEntity(BookDao bookDao) {
        Book createdBook = new Book();
        createdBook.setId(bookDao.getId());
        createdBook.setDescription(bookDao.getDescription());
        createdBook.setPublicationDate(bookDao.getPublicationDate());

        Long authorId = bookDao.getAuthorId();
        Optional<AuthorDao> optionalAuthorDao = authorRepository.getById(authorId);
        AuthorDao authorDao = optionalAuthorDao.orElseThrow(() ->
                new ServerException(messageSource.getMessage(SERVER_ERROR)));

        Author author = authorDaoMapper.toEntity(authorDao);
        createdBook.setAuthor(author);

        Long seriesId = bookDao.getSeriesId();
        Optional<SeriesDao> optionalSeriesDao = seriesRepository.getById(seriesId);
        Series series;
        if (optionalSeriesDao.isEmpty()) {
            series = null;
        } else {
            SeriesDao seriesDao = optionalSeriesDao.get();
            series = seriesDaoMapper.toEntity(seriesDao);
        }
        createdBook.setSeries(series);

        List<GenreDao> genresDao = genreRepository.getByBookId(createdBook.getId());
        List<Genre> genres = genresDao.stream()
                .map(genreDaoMapper::toEntity)
                .toList();
        createdBook.setGenres(genres);

        return createdBook;
    }

    @Override
    public BookDao toDao(Book book) {
        BookDao bookDao = new BookDao();
        if (book.getId() != null) {
            bookDao.setId(book.getId());
        }
        bookDao.setTitle(book.getTitle());
        bookDao.setAuthorId(book.getAuthor().getId());
        bookDao.setSeriesId(book.getSeries().getId());
        bookDao.setDescription(book.getDescription());
        bookDao.setPublicationDate(book.getPublicationDate());
        return bookDao;
    }
}
