CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE scopes (
    "code" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    "resource_server_code" UUID NOT NULL,
    "name" VARCHAR(512) NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (resource_server_code) REFERENCES resource_servers(code)
);