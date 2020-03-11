package com.p2lem8dev.unsplashapi

import com.p2lem8dev.unsplashapi.models.FullPhoto
import com.p2lem8dev.unsplashapi.models.Photo
import com.p2lem8dev.unsplashapi.models.PhotoStats
import com.p2lem8dev.unsplashapi.models.User
import retrofit2.http.*

interface PhotosApi {
    @GET("/photos")
    suspend fun listPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String,
        @Query("client_id") clientId: String
    ): List<Photo>

    @GET("/photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: String,
        @Query("client_id") clientId: String
    ): FullPhoto

    @GET("/photos/{id}/statistics")
    suspend fun getPhotoStatistics(
        @Path("id") id: String,
        @Query("resolution") resolution: String? = "days",
        @Query("quantity") quantity: Int? = 30,
        @Query("client_id") clientId: String
    ): PhotoStats

    @GET("photos/{id}/download")
    suspend fun getPhotoDownloadLink(
        @Path("id") id: String,
        @Query("client_id") clientId: String
    ): DownloadPhotoModel

    class DownloadPhotoModel(val url: String)

    @POST("photos/{id}/like")
    suspend fun likePhoto(
        @Path("id") id: String,
        @Query("client_id") clientId: String
    ): LikePhotoResponse

    @DELETE("photos/{id}/like")
    suspend fun unlikePhoto(
        @Path("id") id: String,
        @Query("client_id") clientId: String
    ): LikePhotoResponse

    class LikePhotoResponse(val photo: Photo, val user: User)

    @GET("photos/random")
    suspend fun getSingleRandomPhoto(
        @Query("client_id") clientId: String
    ): FullPhoto
}
