package com.example.photoshell.data.retrofitclasses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchPhotoResult(
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: List<UnsplashPhoto>
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(total)
        parcel.writeInt(totalPages)
        results.forEach {
            parcel.writeParcelable(it, flags)
        }
    }
}
