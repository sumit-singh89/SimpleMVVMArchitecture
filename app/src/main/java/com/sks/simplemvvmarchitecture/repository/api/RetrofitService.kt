package com.sks.simplemvvmarchitecture.repository.api

/**
 * @author  Sumit Singh on 8/11/2020.
 */

import com.sks.simplemvvmarchitecture.utils.AppConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author  Sumit Singh on 8/11/2020.
 */

object RetrofitService {


    private val retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(getOkHttpBuilder().build()).build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

    private fun getLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private fun getOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().addInterceptor(getLoggingInterceptor())
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                val response = chain.proceed(requestBuilder.build())
                val responseBody = response.body
                val bodyString = responseBody?.string()
                response.newBuilder().body(bodyString?.let {
                    it
                        .toResponseBody(responseBody.contentType())
                }).build()
            }

    }
}
