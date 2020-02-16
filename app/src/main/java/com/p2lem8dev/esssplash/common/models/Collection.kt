package com.p2lem8dev.esssplash.common.models

import com.google.gson.annotations.SerializedName
import java.util.*

class Collection(
    val id: Int,
    val title: String,
    val description: String?,
    @SerializedName("published_at")
    val published: Date?,
    val featured: Boolean,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    val private: Boolean,
    @SerializedName("share_key")
    val shareKey: String?,
    @SerializedName("cover_photo")
    val cover: Photo?,
    val user: User,
    val links: Links
) {
    class Links(
        val self: String,
        val html: String,
        val photos: String,
        val related: String
    )
}
