package com.gestash.newsfeed.domain

import com.gestash.newsfeed.Constants.APIKEY
import com.gestash.newsfeed.Constants.APIKEY_STRING
import com.gestash.newsfeed.Constants.FROM_STRING
import com.gestash.newsfeed.Constants.LANGUAGE_STRING
import com.gestash.newsfeed.Constants.PUBLISHEDAT_STRING
import com.gestash.newsfeed.Constants.Q_STRING
import com.gestash.newsfeed.Constants.SORTBY_STRING
import java.util.*

class NewsRepository(private val service: NewsFeedService) {
    suspend fun getNews(data: String, keyWord: String): NewsData? {
        val lang = Locale.getDefault().language
        val options =
            mapOf(
                Q_STRING to keyWord,
                FROM_STRING to data,
                SORTBY_STRING to PUBLISHEDAT_STRING,
                LANGUAGE_STRING to lang,
                APIKEY_STRING to APIKEY
            )

        return service.getNews(options)
    }
}
