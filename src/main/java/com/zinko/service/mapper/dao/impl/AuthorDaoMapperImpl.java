package com.zinko.service.mapper.dao.impl;

import com.zinko.data.dao.AuthorDao;
import com.zinko.model.Author;
import com.zinko.service.mapper.dao.AuthorDaoMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoMapperImpl implements AuthorDaoMapper {
    @Override
    public Author toEntity(AuthorDao authorDao) {
        Author author = new Author();
        author.setId(authorDao.getId());
        author.setFirstName(authorDao.getFirstName());
        author.setLastName(authorDao.getLastName());
        author.setBiography(authorDao.getBiography());
        return author;
    }

    @Override
    public AuthorDao toDao(Author author) {
        AuthorDao authorDao = new AuthorDao();
        if (author.getId() != null) {
            authorDao.setId(author.getId());
        }
        authorDao.setFirstName(author.getFirstName());
        authorDao.setLastName(author.getLastName());
        authorDao.setBiography(author.getBiography());
        return authorDao;
    }
}
