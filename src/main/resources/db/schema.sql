CREATE TABLE IF NOT EXISTS Employee (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        department VARCHAR(255),
                                        salary DOUBLE PRECISION,
                                        username VARCHAR(255) NOT NULL,
                                        password VARCHAR(255) NOT NULL
);