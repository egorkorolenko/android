package com.example.mybrowser

import android.annotation.TargetApi
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient : WebViewClient() {
    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        view.loadUrl(request.url.toString())
        return true
    }
}