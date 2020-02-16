package com.p2lem8dev.esssplash.app

import com.google.gson.GsonBuilder
import com.p2lem8dev.esssplash.photos.PhotosApi
import com.p2lem8dev.esssplash.search.SearchApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitProvider(private val isDebuggable: Boolean) {
    private val gson by lazy {
        GsonBuilder()
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
            .let(GsonConverterFactory::create)
    }

    @Suppress("SameParameterValue")
    private fun constructRetrofit(url: String) = Retrofit.Builder()
        .baseUrl(url)
        .client(constructHttp())
        .addConverterFactory(gson)
        .build()

    private fun constructHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.connectTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(Constants.NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(Constants.NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS)
        return builder.build()
    }

    private val common by lazy { constructRetrofit(Constants.NETWORK_URL_COMMON) }

    fun homeApi() = common.create(PhotosApi::class.java)
    fun searchApi() = common.create(SearchApi::class.java)
}
