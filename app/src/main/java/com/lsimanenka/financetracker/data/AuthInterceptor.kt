package com.lsimanenka.financetracker.data

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = "6R9ucF6hNbBS4WwKBannqbQk"
        val reqBuilder = chain.request().newBuilder()
        if (!token.isNullOrBlank()) {
            reqBuilder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(reqBuilder.build())
    }
}
