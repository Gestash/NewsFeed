package com.gestash.newsfeed.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gestash.newsfeed.room.NewsUiData

class ArticleViewModel(selectedArticle: NewsUiData) : ViewModel() {
    private val _selectedArticle = MutableLiveData<NewsUiData>()
    val selectedArticle: LiveData<NewsUiData>
        get() = _selectedArticle

    init {
        _selectedArticle.value = selectedArticle
    }
}