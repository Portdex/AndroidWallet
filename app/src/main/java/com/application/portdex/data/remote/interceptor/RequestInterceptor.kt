package com.application.portdex.data.remote.interceptor

import com.jacopo.pagury.prefs.PrefUtils
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val originalUrl: HttpUrl = originalRequest.url
        val url = originalUrl.newBuilder().build()

        val requestBuilder: Request.Builder = originalRequest.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("x-access-token", PrefUtils.getLoginInfo()?.accessToken ?: "")
            .url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}