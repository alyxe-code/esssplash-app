package com.p2lem8dev.unsplashapi.models

import com.google.gson.annotations.SerializedName
import java.util.*

open class User(
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
    val twitter: String?,
    @SerializedName("portfolio_url")
    val portfolio: String?,
    val bio: String?,
    val location: String?,
    val links: Links,
    @SerializedName("profile_image")
    val profileImage: ProfileImage,
    @SerializedName("instagram_username")
    val instagram: String?,
    @SerializedName("total_collections")
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
