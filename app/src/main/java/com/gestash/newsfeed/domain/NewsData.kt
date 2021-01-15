package com.gestash.newsfeed.domain
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsData(
    val status: String,
    val totalResults: Int,
    val articles: List<Articles>
): Parcelable

@Parcelize
data class Articles(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
): Parcelable