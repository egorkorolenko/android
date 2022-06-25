package ru.korolenkoe.news.menuAction

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment


class DownloadNews{

    fun startDownload(url: String, context: Context, title:String?){
        val request = DownloadManager.Request(Uri.parse(url))
            .setDestinationInExternalPublicDir("News","Новость: $title.html")
            .setDescription("File")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)

        val dm = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }
}