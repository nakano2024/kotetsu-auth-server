CREATE TABLE refresh_tokens (
    code VARCHAR(512) NOT NULL PRIMARY KEY,
    access_token_draft_code UUID NOT NULL,
    id_token_draft_code UUID NOT NULL,
    issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expired_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (access_token_draft_code) REFERENCES access_token_drafts(code),
    FOREIGN KEY (id_token_draft_code) REFERENCES id_token_drafts(code)
);
