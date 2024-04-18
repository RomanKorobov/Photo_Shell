package com.example.photoshell.ui.home

import android.content.Context
import android.util.Log
import com.example.photoshell.data.Network
import com.example.photoshell.data.TokenStorage
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto

class HomeRepository(context: Context){
    private val token = TokenStorage.getToken(context)
    private val accessToken = "Bearer $token"
    private val api = Network.api
    private var page = 0
    suspend fun getPhotos(): List<UnsplashPhoto> {
        page++
        return api.getPhotos(accessToken, page)
    }
    suspend fun getPhoto(id: String): UnsplashPhoto {
        return api.getPhoto(accessToken, id)
    }
}