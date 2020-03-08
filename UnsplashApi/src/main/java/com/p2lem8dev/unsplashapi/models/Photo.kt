package com.p2lem8dev.unsplashapi.models

import com.google.gson.annotations.SerializedName
import java.util.*

class Photo(
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
    override val urls: BasePhoto.Urls,
    val links: BasePhoto.Links,
    val categories: List<String>,
    var likes: Int,
    @SerializedName("liked_by_user")
    var likedByUser: Boolean,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<String>,
    val user: User,
    val description: String?,
    @SerializedName("alt_description")
    val altDescription: String?
) : BasePhoto
