package com.example.photoshell.data.retrofitclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashUser(
    val id: String,
    val username: String,
    val name: String,
    @SerializedName("portfolio_url")
    val portfolioUrl: String?,
    val bio: String?,
    val location: String?,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("total_photo")
    val totalPhotos: Int,
    @SerializedName("total_collection")
    val totalCollection: Int,
    @SerializedName("profile_image")
    val profileImage: UnsplashUrls,
    val links: UnsplashLinks
) : Parcelable
