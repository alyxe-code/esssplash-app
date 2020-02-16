package com.p2lem8dev.esssplash.photos.models

import java.util.*

class User(
    val id: String,
    val updated: Date,
    val username: String,
    val name: String?,
    val firstName: String?,
    val lastName: String?,
    val twitterUsername: String?,
    val portfolio: String?,
    val bio: String?,
    val location: String?,
    val links: Links,
    val profileImage: ProfileImage?,
    val instagram: String?,
    val totalCollections: Int,
    val totalLikes: Int,
    val totalPhotos: Int,
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
