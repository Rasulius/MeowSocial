package com.rossgramm.rossapp.base.web.interceptors

import com.rossgramm.rossapp.managers.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class ApiTokenInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //TODO: уточнить как именно передавать токен
        val originalHttpUrl = chain.request().url
        val url = originalHttpUrl
            .newBuilder()
            .build()
        // Request customization: add request headers
        val  token = SessionManager.getAccessToken()
        val request = if (token != null) {
            chain.request().newBuilder()
                .header("X-Auth-Token", token)
                .url(url)
                .build()

        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}