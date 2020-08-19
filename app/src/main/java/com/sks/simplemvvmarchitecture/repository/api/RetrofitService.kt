package com.sks.simplemvvmarchitecture.repository.api

/**
 * @author  Sumit Singh on 8/11/2020.
 */

import com.google.gson.GsonBuilder
import com.sks.simplemvvmarchitecture.MyApplication
import com.sks.simplemvvmarchitecture.utils.AppConstants
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author  Sumit Singh on 8/11/2020.
 */

object RetrofitService {

    // specify a cache size 5 mb
    private const val cacheSize = (5 * 1024 * 1024).toLong()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(getOkHttpBuilder().build()).build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }


    private fun getOkHttpBuilder(): OkHttpClient.Builder {
        val mCache = Cache(MyApplication.mContext?.cacheDir, cacheSize)
        // Add all interceptors you want (headers, URL, logging)
        return OkHttpClient.Builder()
            .addInterceptor(provideOfflineCacheInterceptor())
            .addNetworkInterceptor(provideCacheInterceptor())
            .cache(mCache)
    }

    private fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (e: Exception) {
                /*
                *  If there is no Internet, get the cache that was stored 1 days ago.
                *  If the cache is older than 1 days, then discard it,
                *  and indicate an error in fetching the response.
                *  The 'max-stale' attribute is responsible for this behavior.
                *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                */
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()

                val offlineRequest = chain.request().newBuilder()
                    .cacheControl(cacheControl)
                    .build()
                chain.proceed(offlineRequest)
            }
        }
    }


    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val originalResponse = chain.proceed(request)
            val cacheControl = originalResponse.header("Cache-Control")

            if (cacheControl == null ||
                cacheControl.contains("no-store") ||
                cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") ||
                cacheControl.contains("max-stale=0")) {

                val cc = CacheControl.Builder()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .cacheControl(cc)
                    .build()
                chain.proceed(request)

            } else {
                originalResponse
            }
        }
    }

}
