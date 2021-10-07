package dev.diegodc.moviesapp.data.sources.remote.util

import dev.diegodc.moviesapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.TOKEN_URL}")
            .build()

        return chain.proceed(request)
    }
}