package com.p2lem8dev.esssplash.common.models

import java.util.*

class Photo(
    val id: String,
    val created: Date,
    val updated: Date,
    val width: Int,
    val height: Int,
    val color: String,
    val description: String?,
    val description2: String?,
    val urls: Urls,
    val links: Links,
    val categories: List<String>,
    val likes: Int,
    val likedByUser: Boolean,
    val currentUserCollections: List<String>,
    val user: User
) {
    data class Urls(
        val raw: String?,
        val full: String?,
        val regular: String?,
        val small: String?,
        val thumb: String?
    )

    data class Links(
        val self: String,
        val html: String,
        val download: String,
        val downloadLocation: String
    )
}
