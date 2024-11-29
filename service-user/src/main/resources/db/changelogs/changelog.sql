--liquibase formatted sql

--changeset user:1
CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255),
    email    VARCHAR(255),
    password VARCHAR(255),
    role     VARCHAR(255) CHECK ( role IN ('ROLE_ADMIN', 'ROLE_USER') )
);
