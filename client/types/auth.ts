
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

export interface OAuthFetchTokenParameter {
    clientId: string;
    clientSecret: string;
    code: string;
    codeChallenge: string;
}

export interface OAuthFetchTokenResponse {
    token: string;
    tokenType: string;
    scope: string;
    expiresIn: number;
    idToken: string;
    refreshToken: string;
}
