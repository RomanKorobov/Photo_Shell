package com.example.photoshell.data.retrofitclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Collection(
    val id: String,
    val title: String,
    val description: String?,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("cover_photo")
    val coverPhoto: UnsplashPhoto,
    val links: UnsplashLinks
) : Parcelable
