package ru.korolenkoe.news.activity

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import ru.korolenkoe.news.R

class OpenDownload:AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.open_download_file)

        webView = findViewById(R.id.web_view_download)
        webView.settings.allowFileAccess = true;
        val file = intent.getStringExtra("file")
        webView.loadUrl("/sdcard/News/$file")
    }

}