CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     nickname VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) NOT NULL,
                                     password VARCHAR(255) NOT NULL,
                                     created TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
