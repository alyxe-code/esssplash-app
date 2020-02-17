package com.p2lem8dev.unsplashapi.models

import com.google.gson.annotations.SerializedName

class Tag(
    val type: String,
    val title: String,
    val source: Source?
) {
    class Source(
        val ancestry: Ancestry,
        val title: String,
        val subtitle: String,
        val description: String,
        @SerializedName("meta_title")
        val metaTitle: String,
        @SerializedName("meta_description")
        val metaDescription: String,
        @SerializedName("cover_photo")
        val coverPhoto: Photo
    ) {
        class Ancestry(
            val type: Type,
            val category: Category,
            val subcategory: Subcategory
        ) {
            class Type(
                val slug: String,
                @SerializedName("pretty_slug")
                val prettySlug: String
            )

            class Category(
                val slug: String,
                @SerializedName("pretty_slug")
                val prettySlug: String
            )

            class Subcategory(
                val slug: String,
                @SerializedName("pretty_slug")
                val prettySlug: String
            )
        }
    }
}
