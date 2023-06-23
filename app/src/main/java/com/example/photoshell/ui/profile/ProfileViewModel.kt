package com.example.photoshell.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.photoshell.data.retrofitclasses.MyProfile
import com.example.photoshell.data.retrofitclasses.UnsplashPhoto
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = ProfileRepository(getApplication())
    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }
    private var profileMutableLiveData = MutableLiveData<MyProfile>()
    val profile: MutableLiveData<MyProfile>
        get() = profileMutableLiveData
    private var likedPhotosMutableLiveData = MutableLiveData<List<UnsplashPhoto>>()
    val likedPhotos: MutableLiveData<List<UnsplashPhoto>>
        get() = likedPhotosMutableLiveData
    private var mutableAvatarImageLiveData = MutableLiveData<String?>(null)
    val avatarImage: MutableLiveData<String?>
        get() = mutableAvatarImageLiveData

    fun getMyProfile() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val theProfile = repository.getMyProfile()
            val user = repository.getUser(theProfile.username)
            avatarImage.postValue(user.profileImage.small)
            val thePhotos = withContext(Dispatchers.IO) {
                repository.getLikedPhotos(user.username)
            }
            profileMutableLiveData.postValue(theProfile)
            likedPhotosMutableLiveData.postValue(thePhotos)
        }
    }

    suspend fun openPhotoById(id: String): UnsplashPhoto {
        return repository.getPhoto(id)
    }

    fun addPhotos() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val newPhotos = repository.getLikedPhotos(profile.value?.username!!)
            val newListOfPhoto: List<UnsplashPhoto> =
                likedPhotos.value?.plus(newPhotos) ?: newPhotos
            likedPhotosMutableLiveData.postValue(newListOfPhoto)
        }
    }

    fun logout() {
        repository.logout()
    }
}