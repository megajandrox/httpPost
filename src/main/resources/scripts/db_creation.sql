-- Create table for Request
CREATE TABLE Request (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    method VARCHAR(50) NOT NULL,
    is_favorite BOOLEAN DEFAULT TRUE
);

-- Create table for Body
CREATE TABLE Body (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    request_id INT,
    FOREIGN KEY (request_id) REFERENCES Request(id) ON DELETE CASCADE
);

-- Create table for Header
CREATE TABLE Header (
    id SERIAL PRIMARY KEY,
    key VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    request_id INT,
    FOREIGN KEY (request_id) REFERENCES Request(id) ON DELETE CASCADE
);

-- Create table for QueryParam
CREATE TABLE QueryParam (
    id SERIAL PRIMARY KEY,
    key VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    request_id INT,
    FOREIGN KEY (request_id) REFERENCES Request(id) ON DELETE CASCADE
);
