CREATE TABLE IF NOT EXISTS orders (
                                      id SERIAL PRIMARY KEY,
                                      user_id INT NOT NULL,
                                      from_station_id BIGINT NOT NULL,
                                      to_station_id BIGINT NOT NULL,
                                      status INT NOT NULL,
                                      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      FOREIGN KEY (from_station_id) REFERENCES stations(id),
                                      FOREIGN KEY (to_station_id) REFERENCES stations(id)
);