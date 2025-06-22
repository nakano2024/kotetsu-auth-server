CREATE TABLE id_token_drafts (
    code UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    issuer VARCHAR(255) NOT NULL,
    subject UUID NOT NULL,
    audience UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subject) REFERENCES users(code),
    FOREIGN KEY (audience) REFERENCES client_informations(code)
);
