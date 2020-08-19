package com.sks.simplemvvmarchitecture.repository.api

/**
 * @author  Sumit Singh on 8/11/2020.
 */

import com.sks.simplemvvmarchitecture.MyApplication
import com.sks.simplemvvmarchitecture.utils.AppConstants
import com.sks.simplemvvmarchitecture.utils.NetworkUtils
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author  Sumit Singh on 8/11/2020.
 */

object RetrofitService {


    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    private const val HEADER_PRAGMA = "Pragma"

    // specify a cache size 5 mb
    private const val cacheSize = (5 * 1024 * 1024).toLong()

    private val retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
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

    private fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response: Response = chain.proceed(chain.request())
            val cacheControl: CacheControl =
                if (NetworkUtils.isNetworkConnected(MyApplication.mContext)) {
                    CacheControl.Builder()
                        .maxAge(0, TimeUnit.SECONDS)
                        .build()
                } else {
                    /*
                    *  If there is no Internet, get the cache that was stored 7 days ago.
                    *  If the cache is older than 7 days, then discard it,
                    *  and indicate an error in fetching the response.
                    *  The 'max-stale' attribute is responsible for this behavior.
                    *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                    */
                    CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS) // offline
                        .build()
                }
            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    private fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            if (!NetworkUtils.isNetworkConnected(MyApplication.mContext)) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }


}
