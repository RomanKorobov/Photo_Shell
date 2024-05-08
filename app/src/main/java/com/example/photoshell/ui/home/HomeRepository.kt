package com.example.photoshell.ui.home

import android.content.Context
import android.util.Log
import com.example.photoshell.data.Network
import com.example.photoshell.data.KeyStoreManager
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto

class HomeRepository(private val context: Context){
    private var token = KeyStoreManager.get(context)
    private var accessToken = "Bearer $token"
    private val api = Network.api
    private var page = 0
    suspend fun getPhotos(): List<UnsplashPhoto> {
        Log.d("ExceptionTag", "get photos at: $accessToken")
        page++
        return api.getPhotos(accessToken, page)
    }
    suspend fun getPhoto(id: String): UnsplashPhoto {
        return api.getPhoto(accessToken, id)
    }

    fun updateAccessToken() {
        token = KeyStoreManager.get(context)
        accessToken = "Bearer $token"
        Log.d("ExceptionTag", "token updated $accessToken")
    }
}