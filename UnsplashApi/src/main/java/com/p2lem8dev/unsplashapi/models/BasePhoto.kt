package com.p2lem8dev.unsplashapi.models

import com.google.gson.annotations.SerializedName
import java.util.*

interface BasePhoto {
    val id: String
    val created: Date
    val updated: Date
    val urls: Urls

    class Urls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    )

    class Links(
        val self: String,
        val html: String,
        val download: String,
        @SerializedName("download_location")
        val downloadLocation: String
    )
}
