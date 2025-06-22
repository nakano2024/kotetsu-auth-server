CREATE TABLE access_token_draft_scopes (
    access_token_draft_code UUID NOT NULL,
    scope_code VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (access_token_draft_code, scope_code),
    FOREIGN KEY (access_token_draft_code) REFERENCES access_token_drafts(code),
    FOREIGN KEY (scope_code) REFERENCES scopes(code)
);
