package com.p2lem8dev.esssplash.search

import com.google.gson.annotations.SerializedName
import com.p2lem8dev.esssplash.common.models.Collection
import com.p2lem8dev.esssplash.common.models.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("collections") collections: List<Int>,
        @Query("orientation") orientation: String
    ): PhotosSearchResult

    class PhotosSearchResult(
        val total: Int,
        @SerializedName("total_pages")
        val totalPages: Int,
        val results: List<Photo>
    )

    @GET("search/collections")
    suspend fun searchCollections(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): CollectionsSearchResult

    class CollectionsSearchResult(
        val total: Int,
        @SerializedName("total_pages")
        val totalPages: Int,
        val results: List<Collection>
    )

    @GET("search/users")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UsersSearchResult

    class UsersSearchResult(
        val total: Int,
        @SerializedName("total_pages")
        val totalPages: Int,
        val results: List<Collection>
    )

}
