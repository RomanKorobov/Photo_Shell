package com.example.photoshell.data.retrofitclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashUrls(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val medium: String?,
    val large: String?,
    val thumb: String?
) : Parcelable
