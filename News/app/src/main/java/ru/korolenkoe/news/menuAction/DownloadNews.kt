package ru.korolenkoe.news.menuAction

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri

class DownloadNews{

    fun startDownload(url: String, context: Context){
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("File")
            .setDescription("Download")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)

        val dm = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }
}