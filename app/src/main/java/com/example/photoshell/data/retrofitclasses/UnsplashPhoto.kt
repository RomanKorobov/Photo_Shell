package com.example.photoshell.data.retrofitclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashPhoto(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    val width: Int,
    val height: Int,
    val color: String? = "#000000",
    val likes: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    val description: String?,
    val urls: UnsplashUrls,
    val links: UnsplashLinks,
    val user: UnsplashUser
) : Parcelable
