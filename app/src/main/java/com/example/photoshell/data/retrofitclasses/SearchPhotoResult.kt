package com.example.photoshell.data.retrofitclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchPhotoResult(
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: List<UnsplashPhoto>
) : Parcelable
