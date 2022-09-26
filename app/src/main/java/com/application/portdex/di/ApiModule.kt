package com.application.portdex.di

import android.app.Application
import android.content.Context
import com.application.portdex.App
import com.application.portdex.BuildConfig
import com.application.portdex.data.remote.ApiEndPoints
import com.application.portdex.data.remote.ApiService
import com.application.portdex.data.remote.interceptor.NetworkInterceptor
import com.application.portdex.data.remote.interceptor.RequestInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024.toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }


    @Provides
    @Singleton
    fun provideNetworkInterceptor(application: Application): NetworkInterceptor {
        return NetworkInterceptor(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache, networkInterceptor: NetworkInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder().apply {
            cache(cache)
            addInterceptor(networkInterceptor)
            addInterceptor(logging)
            addNetworkInterceptor(RequestInterceptor())
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(ApiEndPoints.BASE_URL)
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}