
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
    codeVerifier: string;
}

export interface OAuthFetchTokenResponse {
    token: string;
    tokenType: string;
    scope: string;
    expiresIn: number;
    idToken: string;
    refreshToken: string;
}

export interface Jwk {
    kid: string,
    kty: string,
    alg: string,
    use: string,
    n: string,
    e: string,
}

export interface FetchCertsResponse {
    keys: Jwk[],
}

export interface UserProfile {
    sub: string,
    name: string,
    email: string,
    imageUrl: string,
} 

export interface UserMeSession {
    sub: string,
    name: string,
    email: string,
    imageUrl: string,
    scope: string,
}
