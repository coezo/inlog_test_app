package br.com.inlogtest.testapp.api

import okhttp3.Interceptor
import okhttp3.Response

class TmdbInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val httpUrl = request.url

        val newUrl = httpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("language", LANGUAGE)
            .addQueryParameter("region", REGION)
            .build()

        val newRequest = request.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }

    companion object{
        const val API_KEY = "2e508b40194129537006b7a38aadd526"
        const val LANGUAGE = "pt-BR"
        const val REGION = "BR"
    }
}