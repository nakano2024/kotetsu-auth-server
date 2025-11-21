
import { defineEventHandler, type H3Event, getQuery } from 'h3'
import { CallbackParameter, OAuth2Parameter, OidcParameter, UserMeSession } from '~/server/types/auth';
import { FetchError } from 'ofetch'
import { fetchCerts, fetchMeFromIdTokenWithVerification, fetchToken } from '../utils/auth';
import { IdTokeVerificationError } from '../error/auth';

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
        const tokenResponse = await fetchToken({
            clientId: config.clientId,
            clientSecret: config.clientSecret,
            code: callbackParameter.code,
            codeVerifier: oauth2SessionParameter.codeVerifier,
        });

        const certsResponse = await fetchCerts();
        const userProfile = await fetchMeFromIdTokenWithVerification({
            keys: certsResponse.keys
        }, tokenResponse.idToken);

        await clearUserSession(event);
        await setUserSession(event, {
            userMe: {
                sub: userProfile.sub,
                name: userProfile.name,
                email: userProfile.email,
                scope: tokenResponse.scope,
            } as UserMeSession,
        })
        return sendRedirect(event, '/mypage');
    }
    catch(error: any) {
        await clearUserSession(event);
        if (error instanceof FetchError) {
            throw createError({
              statusCode: 502,
              message: "トークンの取得に失敗しました。",
            });
        }

        if (error instanceof IdTokeVerificationError) {
            throw createError({
                statusCode: 401,
                message: 'トークンの検証に失敗しました。',
            });
        }

        throw error;
    }
});
