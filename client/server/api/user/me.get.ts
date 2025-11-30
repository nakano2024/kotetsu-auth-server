import { defineEventHandler, createError, sendError } from 'h3';
import type { H3Event } from 'h3';
import type { UserMeSession } from '~/server/types/auth';

export default defineEventHandler(async (event: H3Event) => {
  const ERROR_MESSAGE = '認証に失敗しました。';

  const session = await getUserSession(event);
  const userMe = session?.userMe as UserMeSession | undefined;

  if (!userMe) {
    return sendError(
      event,
      createError({
        statusCode: 401,
        statusMessage: 'Unauthorized',
        data: { message: ERROR_MESSAGE },
      })
    );
  }

  return {
    me: {
        sub: userMe.sub,
        name: userMe.name,
        email: userMe.email,
        image_url: userMe.imageUrl,
        scope: userMe.scope,
    }
  };
});
