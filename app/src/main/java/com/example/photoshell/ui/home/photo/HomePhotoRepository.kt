package com.example.photoshell.ui.home.photo

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.example.photoshell.data.Network
import com.example.photoshell.data.TokenStorage

class HomePhotoRepository(private val context: Context) {
    private val token = TokenStorage.getToken(context)
    private val accessToken = "Bearer $token"
    private val api = Network.api
    private val downloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    suspend fun likePhoto(id: String) {
        api.likePhoto(accessToken, id)
    }

    suspend fun unlikePhoto(id: String) {
        api.unlikePhoto(accessToken, id)
    }

    suspend fun downloadPhoto(link: String, photoId: String) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            return
        }
        val uri = Uri.parse(link)
        val request =
            DownloadManager.Request(uri).setDestinationInExternalFilesDir(context, null, photoId)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
        val fileId = downloadManager.enqueue(request)
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == fileId) {
                    Toast.makeText(context, "Файл $photoId успешно загружен", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        context.registerReceiver(
            broadcastReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        api.trackPhotoToDownload(accessToken, photoId)
    }

    fun sharePhotoIntent(link: String): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Поделиться ссылкой")
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        return shareIntent
    }
}