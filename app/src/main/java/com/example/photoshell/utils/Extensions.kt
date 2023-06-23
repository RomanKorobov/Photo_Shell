package com.example.photoshell.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutId: Int): View {
    val inflater = LayoutInflater.from(context)
    return inflater.inflate(layoutId, this, false)
}