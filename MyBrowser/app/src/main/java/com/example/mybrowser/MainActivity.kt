package com.example.mybrowser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class MainActivity : AppCompatActivity() {

    private lateinit var webView : WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        webView.webViewClient = MyWebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("http://developer.alexanderklimov.ru/android")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}