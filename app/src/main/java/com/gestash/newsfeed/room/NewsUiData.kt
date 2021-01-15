package com.gestash.newsfeed.room

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsUiData(
    var author: String?,
    var url: String?,
    var title: String?,
    var imageUrl: String?,
    var content: String?
) : Parcelable