CREATE TABLE authorization_codes (
    code UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    value VARCHAR(255) NOT NULL UNIQUE,
    challenge VARCHAR(255) NOT NULL,
    access_token_draft_code UUID NOT NULL,
    id_token_draft_code UUID NOT NULL,
    enable_openid BOOLEAN NOT NULL DEFAULT FALSE,
    enable_offline_access BOOLEAN NOT NULL DEFAULT FALSE,
    issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expired_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (access_token_draft_code) REFERENCES access_token_drafts(code),
    FOREIGN KEY (id_token_draft_code) REFERENCES id_token_drafts(code)
);
