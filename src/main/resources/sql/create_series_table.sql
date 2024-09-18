CREATE TABLE series (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    author_id BIGINT REFERENCES authors
);