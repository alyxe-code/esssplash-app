package com.p2lem8dev.esssplash.photos

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface PhotosApi {

    @GET("/photos")
    suspend fun loadPage(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String,
        @Query("client_id") clientId: String
    ): List<Photo>

    class Photo(
        val id: String,
        @SerializedName("created_at")
        val created: Date,
        @SerializedName("updated_at")
        val updated: Date,
        val width: Int,
        val height: Int,
        val color: String,
        val description: String?,
        @SerializedName("alt_description")
        val altDescription: String?,
        val urls: Urls,
        val links: Links,
        val categories: List<String>,
        val likes: Int,
        @SerializedName("liked_by_user")
        val likedByUser: Boolean,
        @SerializedName("current_user_collections")
        val currentUserCollections: List<String>,
        val user: User
    ) {
        class Urls(
            val raw: String?,
            val full: String?,
            val regular: String?,
            val small: String?,
            val thumb: String?
        )

        class Links(
            val self: String,
            val html: String,
            val download: String,
            @SerializedName("download_location")
            val downloadLocation: String
        )
    }

    class User(
        val id: String,
        @SerializedName("updated_at")
        val updated: Date,
        val username: String,
        val name: String?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("last_name")
        val lastName: String?,
        @SerializedName("twitter_username")
        val twitterUsername: String?,
        @SerializedName("portfolio_url")
        val portfolio: String?,
        val bio: String?,
        val location: String?,
        val links: Links,
        @SerializedName("profile_image")
        val profileImage: ProfileImage?,
        @SerializedName("instagram_username")
        val instagram: String?,
        val totalCollections: Int,
        @SerializedName("total_likes")
        val totalLikes: Int,
        @SerializedName("total_photos")
        val totalPhotos: Int,
        @SerializedName("accepted_tos")
        val acceptedTos: Boolean
    ) {
        class Links(
            val self: String,
            val html: String,
            val photos: String,
            val likes: String,
            val portfolio: String,
            val following: String,
            val followers: String
        )

        class ProfileImage(
            val small: String,
            val medium: String,
            val large: String
        )
    }


}
