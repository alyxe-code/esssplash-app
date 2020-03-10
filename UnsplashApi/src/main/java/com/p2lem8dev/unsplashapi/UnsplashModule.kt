package com.p2lem8dev.unsplashapi

import com.p2lem8dev.unsplashapi.repository.PhotosRepositoryImpl
import com.p2lem8dev.unsplashapi.repository.UnsplashPhotosRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class UnsplashModule(
    private val appKey: String,
    private val secretKey: String,
    private val isDebug: Boolean,
    private val httpLogger: (message: String) -> Unit
) {

    private val okHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) = httpLogger(message)
        })
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.UNSPLASH_URL)

        if (isDebug)
            builder.client(okHttpClient)

        builder.build()
    }

    @Provides
    fun photosApi() = retrofit.create(PhotosApi::class.java)

    @Provides
    fun photosRepository(photosApi: PhotosApi): UnsplashPhotosRepository =
        PhotosRepositoryImpl(photosApi, appKey)
}
