package ru.korolenkoe.news.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.korolenkoe.news.R

class DownloadActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.download_fragment)
    }
}