--liquibase formatted sql

--changeset user:3
CREATE TABLE request_item
(
    id  BIGSERIAL PRIMARY KEY,
    uri VARCHAR(255)
);


