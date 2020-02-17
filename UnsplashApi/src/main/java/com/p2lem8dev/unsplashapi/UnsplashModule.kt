package com.p2lem8dev.unsplashapi

import com.p2lem8dev.unsplashapi.repository.PhotosRepository
import com.p2lem8dev.unsplashapi.repository.PhotosRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class UnsplashModule(
    private val appKey: String,
    private val secretKey: String
) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.UNSPLASH_URL)
            .build()
    }

    @Provides
    fun photosApi() = retrofit.create(PhotosApi::class.java)

    @Provides
    fun photosRepository(photosApi: PhotosApi): PhotosRepository =
        PhotosRepositoryImpl(photosApi, appKey)
}
