package com.example.photoshell.data.retrofitclasses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(createdAt)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeString(color)
        parcel.writeInt(likes)
        parcel.writeBoolean(likedByUser)
        parcel.writeString(description)
        parcel.writeParcelable(urls, flags)
        parcel.writeParcelable(links, flags)
        parcel.writeParcelable(user, flags)
    }
}
