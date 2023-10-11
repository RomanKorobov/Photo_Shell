package com.example.photoshell.data.retrofitclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyProfile(
    val id: String,
    val username: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val bio: String,
    val email: String,
    val links: UnsplashLinks
) : Parcelable
