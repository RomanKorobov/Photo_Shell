package com.example.photoshell.ui.profile

import android.content.Context
import com.example.photoshell.data.retrofitclasses.MyProfile
import com.example.photoshell.data.Network
import com.example.photoshell.data.TokenStorage
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto
import com.example.photoshell.data.retrofitclasses.UnsplashUser

class ProfileRepository(private val context: Context) {
    private val token = TokenStorage.getToken(context)
    private val accessToken = "Bearer $token"
    private val api = Network.api
    private var page = 0
    suspend fun getMyProfile(): MyProfile {
        return api.getMyProfile(accessToken)
    }

    suspend fun getLikedPhotos(username: String): List<UnsplashPhoto> {
        page++
        return api.getUserLikedPhotos(accessToken, username, page)
    }

    suspend fun getPhoto(id: String): UnsplashPhoto {
        return api.getPhoto(accessToken, id)
    }

    suspend fun getUser(username: String): UnsplashUser {
        return api.getUser(accessToken, username)
    }

    fun logout() {
        TokenStorage.deleteToken(context)
    }
}