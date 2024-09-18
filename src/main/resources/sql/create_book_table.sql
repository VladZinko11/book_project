CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    author_id BIGINT REFERENCES authors,
    series_id BIGINT references series,
    publication_date DATE,
    description VARCHAR(255)
);