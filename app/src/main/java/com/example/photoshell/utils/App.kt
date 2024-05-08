package com.example.photoshell.utils

import android.app.Application
import com.example.photoshell.data.KeyStoreManager
import com.example.photoshell.data.Network


class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Network
        KeyStoreManager
    }
}