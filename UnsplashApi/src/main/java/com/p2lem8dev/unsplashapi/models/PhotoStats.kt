package com.p2lem8dev.unsplashapi.models


class PhotoStats(
    val id: String,
    val downloads: Downloads,
    val views: Views,
    val likes: Likes
) {
    class Downloads(
        val total: Int,
        val historical: Historical
    ) {
        class Historical(
            val change: Int,
            val resolution: String,
            val quantity: Int,
            val values: List<Value>
        ) {
            class Value(
                val date: String,
                val value: Int
            )
        }
    }

    class Views(
        val total: Int,
        val historical: Historical
    ) {
        class Historical(
            val change: Int,
            val resolution: String,
            val quantity: Int,
            val values: List<Value>
        ) {
            class Value(
                val date: String,
                val value: Int
            )
        }
    }

    class Likes(
        val total: Int,
        val historical: Historical
    ) {
        class Historical(
            val change: Int,
            val resolution: String,
            val quantity: Int,
            val values: List<Value>
        ) {
            class Value(
                val date: String,
                val value: Int
            )
        }
    }
}
