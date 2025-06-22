CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE client_permitted_scopes (
    "client_information_code" UUID NOT NULL,
    "scope_code" UUID NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (client_information_code, scope_code),
    FOREIGN KEY (client_information_code) REFERENCES client_informations(code),
    FOREIGN KEY (scope_code) REFERENCES scopes(code)
);
