package com.gestash.newsfeed.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gestash.newsfeed.NewsApiStatus
import com.gestash.newsfeed.R
import com.gestash.newsfeed.domain.NewsData
import com.gestash.newsfeed.domain.NewsRepository
import com.gestash.newsfeed.room.NewsUiData
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(private val newsRepository: NewsRepository, private val context: Context) :
    ViewModel() {

    private val _newsData = MutableLiveData<List<NewsUiData>>()
    val listNewsData: LiveData<List<NewsUiData>>
        get() = _newsData

    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus>
        get() = _status

    private val _navigateToSelectedArticle = MutableLiveData<NewsUiData>()

    val navigateToSelectedProperty: LiveData<NewsUiData>
        get() = _navigateToSelectedArticle

    init {
        getCurrentData()
    }

    private fun getCurrentData(): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-d")
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun getNewsFromNet(keyWord: String) {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                val data =
                    parseData(newsRepository.getNews(getCurrentData(), keyWord) ?: return@launch)
                _status.value = NewsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _newsData.value = ArrayList()
            }
        }
    }

    private fun parseData(data: NewsData) {
        if (data.articles.isEmpty()) {
            Toast.makeText(
                context,
                context.getString(R.string.error_message),
                Toast.LENGTH_LONG
            ).show()
        } else {
            val uiList: List<NewsUiData> = data.articles.map { article ->
                NewsUiData(
                    author = article.author,
                    url = article.url,
                    title = article.title,
                    imageUrl = article.urlToImage,
                    content = article.content
                )
            }
            _newsData.postValue(uiList)
        }

    }

    fun displayPropertyDetails(data: NewsUiData) {
        _navigateToSelectedArticle.value = data
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedArticle.value = null
    }
}