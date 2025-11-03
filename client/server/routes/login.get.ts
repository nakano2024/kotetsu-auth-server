import type { H3Event } from 'h3'

export default defineEventHandler((event: H3Event) => {

    const config = useRuntimeConfig();

    

    const queryParams = new URLSearchParams({
        client_id: config.clientId,
        redirect_uri: config.redirectUri,
        scope: 'openid',
        response_type: 'code',
        access_type: 'offline',
        nonce: 'test-nonce',
        state: 'test-state',
        code_challenge: 'test-challenge'
    });

    return sendRedirect(event, `${config.idpUrl}/oauth2/authorization?${queryParams.toString()}`, 302);
})
