package com.p2lem8dev.unsplashapi.models

import com.google.gson.annotations.SerializedName
import java.util.*

class Collection(
    val id: Int,
    val title: String,
    @SerializedName("published_at")
    val publishedAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
    val curated: Boolean,
    val featured: Boolean,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    val private: Boolean,
    @SerializedName("share_key")
    val shareKey: String,
    val tags: List<Tag>,
    val links: Links,
    val user: User,
    @SerializedName("cover_photo")
    val coverPhoto: Photo,
    @SerializedName("preview_photos")
    val previewPhotos: List<PreviewPhoto>,
    val description: Any?
) {
    data class Links(
        val self: String,
        val html: String,
        val photos: String,
        val related: String
    )

    data class PreviewPhoto(
        val id: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("updated_at")
        val updatedAt: String,
        val urls: Urls
    ) {
        data class Urls(
            val raw: String,
            val full: String,
            val regular: String,
            val small: String,
            val thumb: String
        )
    }
}
