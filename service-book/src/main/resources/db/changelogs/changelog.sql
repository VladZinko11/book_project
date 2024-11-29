--liquibase formatted sql

--changeset user:2
CREATE TABLE authors
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    biography  VARCHAR(255)
);

CREATE TABLE genres
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE series
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255),
    description VARCHAR(255),
    author_id   BIGINT REFERENCES authors
);

CREATE TABLE books
(
    id               BIGSERIAL PRIMARY KEY,
    title            VARCHAR(255),
    author_id        BIGINT REFERENCES authors,
    series_id        BIGINT references series,
    publication_date DATE,
    description      VARCHAR(255),
    image_id         VARCHAR(255)
);

CREATE TABLE books_genres
(
    book_id  BIGINT REFERENCES books,
    genre_id BIGINT REFERENCES genres
);

