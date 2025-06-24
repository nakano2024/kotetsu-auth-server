CREATE TABLE access_token_scopes (
    access_token_code UUID NOT NULL,
    scope_code UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (access_token_code, scope_code),
    FOREIGN KEY (access_token_code) REFERENCES access_tokens(code),
    FOREIGN KEY (scope_code) REFERENCES scopes(code)
);
