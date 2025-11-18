import { randomBytes } from 'crypto'
import { defineEventHandler, type H3Event } from 'h3'
import base64url from 'base64url'
import { useRuntimeConfig, setUserSession, sendRedirect } from '#imports'
import type { OAuth2Parameter, OidcParameter } from '@/types/auth'

export default defineEventHandler(async (event: H3Event): Promise<void> => {

    const config = useRuntimeConfig();

    const state = await randomBytes(32).toString('base64url');
    const nonce = await randomBytes(64).toString('base64url');
    const codeVerifier = await randomBytes(64).toString('base64url');
    const codeChallenge = await (async () => {
      const data = new TextEncoder().encode(codeVerifier);
      const digest = await crypto.subtle.digest('SHA-256', data);
      return Buffer.from(digest).toString('base64url');
    })();

    const queryParams = new URLSearchParams({
        client_id: config.clientId as string,
        redirect_uri: config.redirectUri as string,
        scope: 'openid',
        response_type: 'code',
        access_type: 'offline',
        nonce: nonce,
        state: state,
        code_challenge: codeChallenge,
    });

    await setUserSession(event, {
        oauth2Parameter: {
            state: state,
            codeVerifier: codeVerifier,
        } as OAuth2Parameter,
        oidcParameter: {
            nonce: nonce,
        } as OidcParameter,
    });

    return sendRedirect(event, `${config.idpUrl}/oauth2/authorization?${queryParams.toString()}`, 302);
})
