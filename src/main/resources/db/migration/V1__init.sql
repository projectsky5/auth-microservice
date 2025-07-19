CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(64),
    password VARCHAR(128),
    email VARCHAR(32),
    role VARCHAR(16)
)