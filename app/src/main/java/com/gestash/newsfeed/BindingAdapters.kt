package com.gestash.newsfeed

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gestash.newsfeed.room.NewsUiData
import com.gestash.newsfeed.ui.home.HomeNewsFeedAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<NewsUiData>?) {
    val adapter = recyclerView.adapter as HomeNewsFeedAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    if (imgUrl == null) {
        imgView.visibility = View.GONE
    } else {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

enum class NewsApiStatus { LOADING, ERROR, DONE }

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: NewsApiStatus?) {
    when (status) {
        NewsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NewsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        NewsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE

        }
    }
}
