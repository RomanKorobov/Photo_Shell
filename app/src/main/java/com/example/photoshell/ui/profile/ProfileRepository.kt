package com.example.photoshell.ui.profile

import android.content.Context
import com.example.photoshell.data.retrofitclasses.MyProfile
import com.example.photoshell.data.Network
import com.example.photoshell.data.KeyStoreManager
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto
import com.example.photoshell.data.retrofitclasses.UnsplashUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepository(private val context: Context) {
    private val token = KeyStoreManager.get(context)
    private val accessToken = "Bearer $token"
    private val api = Network.api
    private var page = 0
    suspend fun getMyProfile(): Flow<MyProfile> = flow {
        emit(api.getMyProfile(accessToken))
    }

    suspend fun getLikedPhotos(username: String): List<UnsplashPhoto> {
        page++
        return api.getUserLikedPhotos(accessToken, username, page, 100)
    }

    suspend fun getPhoto(id: String): UnsplashPhoto {
        return api.getPhoto(accessToken, id)
    }

    suspend fun getUser(username: String): UnsplashUser {
        return api.getUser(accessToken, username)
    }

    fun logout() {

    }
}