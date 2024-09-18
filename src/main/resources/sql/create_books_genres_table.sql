CREATE TABLE books_genres (
    book_id BIGINT REFERENCES books,
    genre_id BIGINT REFERENCES genres
);