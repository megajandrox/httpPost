CREATE TABLE IF NOT EXISTS request (
    id INTEGER IDENTITY,
    url VARCHAR(1024),
    method VARCHAR(20),
    json_data CLOB,
    is_favorite BOOLEAN,
    PRIMARY KEY (id)
);
