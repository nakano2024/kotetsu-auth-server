
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    "code" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    "name" VARCHAR(64) NOT NULL,
    "email" VARCHAR(128) NOT NULL UNIQUE,
    "password" VARCHAR(128) NOT NULL,
    "image_file_code" UUID NOT NULL,
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (image_file_code) REFERENCES files(code)
);
