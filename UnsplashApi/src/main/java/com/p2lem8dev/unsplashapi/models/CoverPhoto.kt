package com.p2lem8dev.unsplashapi.models

import java.util.*

class CoverPhoto(
    override val id: String,
    override val created: Date,
    override val updated: Date,
    val promotedAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    val description: Any?,
    val altDescription: String,
    override val urls: BasePhoto.Urls,
    val links: BasePhoto.Links,
    val categories: List<Any>,
    val likes: Int,
    val likedByUser: Boolean,
    val currentUserCollections: List<Any>,
    val user: User
) : BasePhoto
