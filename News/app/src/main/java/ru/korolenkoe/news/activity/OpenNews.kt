package ru.korolenkoe.news.activity

import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import ru.korolenkoe.news.R

class OpenNews : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_news)

        webView = findViewById(R.id.webView)
        val url = intent.getStringExtra("url")
        webView.loadUrl(Uri.parse(url).toString())
    }
}