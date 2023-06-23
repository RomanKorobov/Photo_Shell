package com.example.photoshell.data.retrofitclasses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashLinks(
    val self: String,
    val html: String,
    val photos: String?,
    val likes: String?,
    val portfolio: String?,
    val download: String?,
    @SerializedName("download_location")
    val downloadLocation: String?
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(self)
        parcel.writeString(html)
        parcel.writeString(photos)
        parcel.writeString(likes)
        parcel.writeString(portfolio)
        parcel.writeString(download)
        parcel.writeString(downloadLocation)
    }
}