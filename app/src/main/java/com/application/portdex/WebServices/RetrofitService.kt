package com.application.portdex.WebServices

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

interface RetrofitService {

        companion object Factory {
        var retrofitCall : RetrofitService? = null
        fun getInstance() : RetrofitService{
            if (retrofitCall == null) {
                val retrofit = Retrofit.Builder()
//                    .baseUrl(Constants.base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitCall = retrofit.create(RetrofitService::class.java)
            }
            return retrofitCall!!
            }

            fun create(context: Context, isheader: Boolean, url : String) : ApiInterface {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val httpClient = OkHttpClient.Builder()
                httpClient.connectTimeout(30, TimeUnit.SECONDS)
                httpClient.writeTimeout(30, TimeUnit.SECONDS)
                httpClient.readTimeout(30, TimeUnit.SECONDS)
                httpClient.addInterceptor(interceptor)
                if(isheader) {
                    httpClient.addInterceptor(object: Interceptor {
                        @Throws(IOException::class)
                        override  fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                            var builders=chain.request()
                            try {
                                builders = chain.request().newBuilder()
                                    .header("Accept", "application/json")
                                    .build()


                            }
                            catch (exception:Exception)
                            {
                                when (exception) {
                                    is SocketTimeoutException -> {
                                    }
                                    is SocketException -> {
                                    }
                                    is IOException -> {
                                    }
                                    else -> exception.stackTrace
                                }
                            }
                            return chain.proceed(builders)
                        }
                    })

                }
                else{
                    httpClient.addInterceptor(object: Interceptor
                    {
                        @Throws(IOException::class)

                        override  fun intercept(chain: Interceptor.Chain): okhttp3.Response
                        {
                            var builders=chain.request()
                            try {
                                builders = chain.request().newBuilder()
                                    .header("Accept", "application/json")
                                    .build()


                            }
                            catch (exception:Exception)
                            {
                                when (exception) {
                                    is SocketTimeoutException -> {
                                        //message.sendToTarget()
                                    }
                                    is SocketException -> {
                                        //message.sendToTarget()
                                    }
                                    is IOException -> {
                                        // message.sendToTarget()
                                    }
                                    else -> exception.stackTrace
                                }
                            }

                            return chain.proceed(builders)
                        }
                    })
                }

                val retrofit= Retrofit.Builder()
                    .baseUrl(url)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                return retrofit.create(ApiInterface::class.java)

            }

        }


    }
