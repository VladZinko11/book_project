CREATE TABLE series_genres (
    series_id BIGINT REFERENCES series,
    genre_id BIGINT REFERENCES genres
);