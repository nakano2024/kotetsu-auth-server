
import { defineEventHandler, type H3Event, getQuery } from 'h3'
import { CallbackParameter, OAuth2Parameter, OAuthFetchTokenResponse, OidcParameter } from '~/types/auth';
import { FetchError } from 'ofetch'

export default defineEventHandler(async (event: H3Event): Promise<void> => {
    try {
        const callbackParameter = getQuery<Partial<CallbackParameter>>(event);

        const ERROR_MESSAGE_400 = '不正なリクエストです。' ;
        if (!callbackParameter.code || !callbackParameter.state) {
            throw createError({ statusCode: 400, message: ERROR_MESSAGE_400, fatal: false });
        }

        const { oauth2Parameter,  oidcParameter } = await getUserSession(event);

        const oauth2SessionParameter = oauth2Parameter as OAuth2Parameter;
        const oidcSessionParameter = oidcParameter as OidcParameter;
        if (!oauth2SessionParameter?.state || !oauth2SessionParameter?.codeVerifier || !oidcSessionParameter?.nonce) {
            throw createError({ statusCode: 400, message: ERROR_MESSAGE_400, fatal: false });
        }

        if (callbackParameter.state !== oauth2SessionParameter.state) {
            throw createError({ statusCode: 400, message: ERROR_MESSAGE_400, fatal: false });
        }

        const config = useRuntimeConfig();
        const basicAuthCredential = Buffer.from(`${config.clientId}:${config.clientSecret}`, 'utf8').toString('base64');
        const tokenResponse = await $fetch<OAuthFetchTokenResponse>(`${config.idpUrl}/api/oauth2/token`, {
            method: 'POST',
            headers: {
                Authorization: `Basic ${basicAuthCredential}`,
            },
            body: new URLSearchParams({
                grant_type: 'authorization_code',
                code: callbackParameter.code,
                code_verifier: oauth2SessionParameter.codeVerifier,
            }),
        });
        await clearUserSession(event);
        return sendWebResponse(event, Response.json(tokenResponse));
    }
    catch(error: any) {
        await clearUserSession(event);
        if (error?.name === 'FetchError') {
            const status = error.status;
            const apiCode = error.data?.code;
            console.error('FetchError status:', status);
            console.error('API error code:', apiCode);

            throw createError({
              statusCode: status,
              message: "トークンの取得に失敗しました。",
              data: { code: apiCode }
            });
        }        
        throw error;
    }
});
