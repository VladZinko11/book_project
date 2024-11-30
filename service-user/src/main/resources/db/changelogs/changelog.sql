--liquibase formatted sql

--changeset user:1
CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR,
    email    VARCHAR,
    password VARCHAR,
    role     VARCHAR CHECK ( role IN ('ROLE_ADMIN', 'ROLE_USER') )
);
