package com.p2lem8dev.unsplashapi

import com.google.gson.GsonBuilder
import com.p2lem8dev.unsplashapi.repository.UnsplashPhotosRepository
import com.p2lem8dev.unsplashapi.repository.UnsplashPhotosRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class UnsplashModule(
    private val appKey: String,
    private val secretKey: String,
    private val isDebug: Boolean
) {

    private val gson by lazy {
        GsonBuilder()
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
            .newBuilder()
            .create()
            .let(GsonConverterFactory::create)
    }

    private val retrofit: Retrofit by lazy {
        val builder = Retrofit.Builder()
            .addConverterFactory(gson)
            .baseUrl(Constants.UNSPLASH_URL)

        if (isDebug)
            builder.client(constructHttpClient())

        builder.build()
    }

    private fun constructHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor().apply {
            level = if (isDebug)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.BASIC
        }
        builder.addInterceptor(logging)

        val networkTimeout = if (isDebug)
            Constants.NETWORK_TIMEOUT_DEBUG
        else
            Constants.NETWORK_TIMEOUT_RELEASE

        builder.connectTimeout(networkTimeout, TimeUnit.SECONDS)
        builder.readTimeout(Constants.NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(Constants.NETWORK_WRITE_TIMEOUT, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    fun photosApi(): PhotosApi = retrofit.create(PhotosApi::class.java)

    @Provides
    fun photosRepository(photosApi: PhotosApi): UnsplashPhotosRepository =
        UnsplashPhotosRepositoryImpl(photosApi, appKey)
}
