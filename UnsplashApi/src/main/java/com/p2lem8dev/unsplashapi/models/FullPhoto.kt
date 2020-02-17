package com.p2lem8dev.unsplashapi.models

import com.google.gson.annotations.SerializedName
import java.util.*

open class FullPhoto(
    override val id: String,
    @SerializedName("created_at")
    override val created: Date,
    @SerializedName("updated_at")
    override val updated: Date,
    @SerializedName("promoted_at")
    val promoted: Date?,
    val width: Int,
    val height: Int,
    val color: String,
    val description: String?,
    @SerializedName("alt_description")
    val altDescription: String?,
    override val urls: BasePhoto.Urls,
    val links: BasePhoto.Links,
    val categories: List<String>,
    val likes: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<String>,
    val user: User,
    val exif: Exif,
    val location: Location,
    val tags: List<Tag>,
    @SerializedName("related_collections")
    val relatedCollections: RelatedCollections,
    val views: Int,
    val downloads: Int
) : BasePhoto {

    class Exif(
        val make: String,
        val model: String,
        @SerializedName("exposure_time")
        val exposureTime: String,
        val aperture: String,
        @SerializedName("focal_length")
        val focalLength: String,
        val iso: Int
    )

    class Location(
        val title: String?,
        val name: String?,
        val city: String?,
        val country: String?,
        val position: Position
    ) {
        data class Position(
            val latitude: Double?,
            val longitude: Double?
        )
    }

    class RelatedCollections(
        val total: Int,
        val type: String,
        @SerializedName("results")
        val collections: List<Collection>
    )
}
