// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },

  runtimeConfig: {
    clientId: process.env.CLIENT_ID,
    clientSecret: process.env.CLIENT_SECRET,
    redirectUri: process.env.REDIRECT_URI,
    idpUrl: process.env.IDP_URL,
    session: {
      password: (process.env.NUXT_SESSION_PASSWORD as string),
      cookie: {
        secure: false,  // ← ローカルでは必須
        sameSite: 'lax',
        httpOnly: true,
        path: '/',
      }
    }
  },

  modules: ['nuxt-auth-utils'],
  srcDir: './'
})
