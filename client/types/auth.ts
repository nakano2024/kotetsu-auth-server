
export interface OAuth2Parameter {
    state: string;
    codeVerifier: string;
}

export interface OidcParameter {
    nonce: string;
}

export interface CallbackParameter {
    code: string;
    state: string;
}