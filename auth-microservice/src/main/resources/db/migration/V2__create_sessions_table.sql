CREATE TABLE IF NOT EXISTS sessions (
                                        id BIGSERIAL PRIMARY KEY,
                                        user_id BIGINT NOT NULL,
                                        token VARCHAR(255) NOT NULL,
                                        expires TIMESTAMPTZ NOT NULL,
                                        FOREIGN KEY (user_id) REFERENCES users(id)
);
