
import { defineEventHandler, type H3Event, getQuery } from 'h3'
import { CallbackParameter, OAuth2Parameter, OidcParameter } from '~/types/auth';

export default defineEventHandler(async (event: H3Event): Promise<void> => {
    const callbackParameter = getQuery<Partial<CallbackParameter>>(event);

    const ERROR_MESSAGE_400 = '不正なリクエストです。' ;
    if (!callbackParameter.code || !callbackParameter.state) {
        throw createError({ statusCode: 400, message: ERROR_MESSAGE_400, fatal: false });
    }

    const { oauth2Parameter,  oidcParameter } = await getUserSession(event);
    await clearUserSession(event);
    console.dir(oauth2Parameter);

    const oauth2SessionParameter = oauth2Parameter as OAuth2Parameter;
    const oidcSessionParameter = oidcParameter as OidcParameter;
    if (!oauth2SessionParameter?.state || !oauth2SessionParameter?.codeVerifier || !oidcSessionParameter?.nonce) {
        throw createError({ statusCode: 400, message: ERROR_MESSAGE_400, fatal: false });
    }

    console.dir(oauth2SessionParameter.state);
    if (callbackParameter.state !== oauth2SessionParameter.state) {
        throw createError({ statusCode: 400, message: ERROR_MESSAGE_400, fatal: false });
    }

    return sendRedirect(event, '/', 302);
});
