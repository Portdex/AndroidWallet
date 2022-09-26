package com.application.portdex.data.remote.interceptor

import android.content.Context
import androidx.annotation.NonNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkInterceptor(private val context: Context) : Interceptor {

    private var time = 60 * 60 * 24 * 7

    @NonNull
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (ConnectivityStatus.isConnected(context)) {
            request.newBuilder()
                .header("Cache-Control", "public, max-age=" + 60)
                .build()
        } else {
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$time")
                .build()
        }
        return chain.proceed(request)
    }
}