package com.gestash.newsfeed.domain

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsFeedService {
    @GET("everything")
    suspend fun getNews(@QueryMap options: Map<String, String>): NewsData
}