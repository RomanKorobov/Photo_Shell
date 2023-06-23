package com.example.photoshell.data.retrofitclasses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(totalPhotos)
        parcel.writeParcelable(coverPhoto, flags)
        parcel.writeParcelable(links, flags)
    }
}
