package com.example.photoshell.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoshell.data.retrofitclasses.MyProfile
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = ProfileRepository(getApplication())
    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }
    lateinit var profileFlow: MutableStateFlow<MyProfile>
    lateinit var likedPhotosFlow: MutableStateFlow<List<UnsplashPhoto>>
    lateinit var avatarImageFlow: MutableStateFlow<String>

    fun getMyProfile() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            profileFlow = repository.getMyProfile() as MutableStateFlow<MyProfile>
            profileFlow.collect {
                val user = repository.getUser(it.username)
                avatarImageFlow.value = user.profileImage.small ?: ""
                likedPhotosFlow.value = async {
                    repository.getLikedPhotos(user.username)
                }.await()
            }
        }
    }

    suspend fun openPhotoById(id: String): UnsplashPhoto {
        return repository.getPhoto(id)
    }

    fun addPhotos() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            profileFlow.collect {
                val newPhotos = repository.getLikedPhotos(it.username)
                val newListOfPhoto: List<UnsplashPhoto> =
                    likedPhotosFlow.value.plus(newPhotos)
                likedPhotosFlow.value = newListOfPhoto
            }
        }
    }

    fun logout() {
        repository.logout()
    }
}