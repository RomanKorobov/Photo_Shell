package com.example.photoshell.ui.home.search

import android.content.Context
import com.example.photoshell.data.Network
import com.example.photoshell.data.TokenStorage
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto

class SearchRepository(context: Context) {
    private val token = TokenStorage.getToken(context)
    private val accessToken = "Bearer $token"
    private val api = Network.api
    private var searchPage = 0
    var queryString = ""
    suspend fun searchPhotos(): List<UnsplashPhoto> {
        searchPage++
        val result = api.searchPhotos(accessToken, queryString, searchPage)
        return result.results
    }

    suspend fun getPhoto(id: String): UnsplashPhoto {
        return api.getPhoto(accessToken, id)
    }
}