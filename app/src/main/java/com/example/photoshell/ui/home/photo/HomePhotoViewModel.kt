package com.example.photoshell.ui.home.photo

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePhotoViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = HomePhotoRepository(getApplication())
    private val handler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    private var mutableLikeLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val likeLiveData: MutableLiveData<Boolean>
        get() = mutableLikeLiveData

    fun likePhoto(id: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            repository.likePhoto(id)
            mutableLikeLiveData.postValue(true)
        }
    }

    fun unlikePhoto(id: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            repository.unlikePhoto(id)
            mutableLikeLiveData.postValue(false)
        }
    }

    fun downloadPhoto(link: String, name: String) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            repository.downloadPhoto(link, name)
        }
    }

    fun sharePhotoIntent(link: String): Intent {
        return repository.sharePhotoIntent(link)
    }

    fun setIfLiked(ifLiked: Boolean) {
        mutableLikeLiveData.postValue(ifLiked)
    }
}