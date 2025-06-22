CREATE TABLE access_token_scopes (
    access_token_code VARCHAR(512) NOT NULL,
    scope_code VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (access_token_code, scope_code),
    FOREIGN KEY (access_token_code) REFERENCES access_tokens(code),
    FOREIGN KEY (scope_code) REFERENCES scopes(code)
);