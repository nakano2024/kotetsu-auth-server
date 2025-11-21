import { createLocalJWKSet, jwtVerify } from "jose";
import { FetchCertsResponse, Jwk, OAuthFetchTokenParameter, OAuthFetchTokenResponse, UserProfile } from "../types/auth";
import { IdTokeVerificationError } from "../error/auth";

interface TokenResponseJson {
    token: string;
    token_type: string;
    scope: string;
    expires_in: number;
    id_token: string;
    refresh_token: string;
}

export const fetchToken = async (parameter: OAuthFetchTokenParameter): Promise<OAuthFetchTokenResponse> => {
    try {
        const config = useRuntimeConfig();
        const basicAuthCredential = Buffer.from(`${parameter.clientId}:${parameter.clientSecret}`, 'utf8').toString('base64');
        const response = await $fetch<TokenResponseJson>(`${config.idpUrl}/api/oauth2/token`, {
            method: 'POST',
            headers: {
                Authorization: `Basic ${basicAuthCredential}`,
            },
            body: new URLSearchParams({
                grant_type: 'authorization_code',
                code: parameter.code,
                code_verifier: parameter.codeVerifier,
            }),
        });

        return {
            token: response.token,
            tokenType: response.token_type,
            scope: response.scope,
            expiresIn: response.expires_in,
            idToken: response.id_token,
            refreshToken: response.refresh_token,
        }
    }
    catch(error: any) {
        throw error;
    }
}

interface CertsResponseJson {
    keys: {
        kid: string,
        kty: string,
        alg: string,
        use: string,
        n: string,
        e: string,
    }[]
}

export const fetchCerts = async (): Promise<FetchCertsResponse> => {
    try {
        const config = useRuntimeConfig();
        const response = await $fetch<CertsResponseJson>(`${config.idpUrl}/api/oauth2/certs`, {
            method: 'GET',
        });

        const jwks = response.keys.map((jwk): Jwk => {
            return {
                kid: jwk.kid,
                kty: jwk.kty,
                alg: jwk.alg,
                use: jwk.use,
                n: jwk.n,
                e: jwk.e,
            }
        });

        return {
            keys: jwks
        };
    }
    catch(error: any) {
        throw error;
    }
}

interface IdTokenProfile {
    name: string,
    email: string,
    image_url: string,
}

interface Certs {
    keys: {
        kid: string,
        kty: string,
        alg: string,
        use: string,
        n: string,
        e: string,
    }[]
}

export const fetchMeFromIdTokenWithVerification = async (certs: Certs, idToken: string): Promise<UserProfile> => {
    try {
        const jwks = createLocalJWKSet(certs);
        const { payload } = await jwtVerify(idToken, jwks);
        const profile = payload?.profile as IdTokenProfile;

        return {
            sub: payload.sub as string,
            name: profile?.name,
            email: profile?.email,
            imageUrl: profile?.image_url,
        }
    }
    catch(error: unknown) {
        const message = error instanceof Error
            ? error.message
            : '予期せぬエラーが発生しました。';

        throw new IdTokeVerificationError(message);
    }
}
