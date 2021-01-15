package com.gestash.newsfeed.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gestash.newsfeed.room.NewsUiData


class ArticleViewModelFactory(
    private val selectedArticle: NewsUiData
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel(selectedArticle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}