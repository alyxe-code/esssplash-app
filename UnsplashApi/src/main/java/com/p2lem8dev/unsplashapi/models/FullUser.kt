package com.p2lem8dev.unsplashapi.models

import java.util.*

class FullUser(
    id: String,
    updated: Date,
    username: String,
    name: String?,
    firstName: String?,
    lastName: String?,
    twitter: String?,
    portfolio: String?,
    bio: String?,
    location: String?,
    links: Links,
    profileImage: ProfileImage,
    instagram: String?,
    totalCollections: Int,
    totalLikes: Int,
    totalPhotos: Int,
    acceptedTos: Boolean,
    val followedByUser: Boolean?,
    val photos: List<BasePhoto>,
    val badge: Any?,
    val tags: UserTags,
    val followersCount: Int,
    val followingCount: Int,
    val allowMessages: Boolean,
    val numericId: Int,
    val downloads: Int
) : User(
    id = id,
    updated = updated,
    username = username,
    name = name,
    firstName = firstName,
    lastName = lastName,
    twitter = twitter,
    portfolio = portfolio,
    bio = bio,
    location = location,
    links = links,
    profileImage = profileImage,
    acceptedTos = acceptedTos,
    totalCollections = totalCollections,
    totalLikes = totalLikes,
    totalPhotos = totalPhotos,
    instagram = instagram
) {
    class UserTags(
        val custom: List<Tag>,
        val aggregated: List<Tag>
    )
}
