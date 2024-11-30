--liquibase formatted sql

--changeset user:2
CREATE TABLE authors
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR,
    last_name  VARCHAR,
    biography  VARCHAR
);

CREATE TABLE genres
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE series
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR,
    description VARCHAR,
    author_id   BIGINT REFERENCES authors
);

CREATE TABLE books
(
    id               BIGSERIAL PRIMARY KEY,
    title            VARCHAR,
    author_id        BIGINT REFERENCES authors,
    series_id        BIGINT references series,
    publication_date DATE,
    description      VARCHAR,
    image_id         VARCHAR
);

CREATE TABLE books_genres
(
    book_id  BIGINT REFERENCES books,
    genre_id BIGINT REFERENCES genres
);

