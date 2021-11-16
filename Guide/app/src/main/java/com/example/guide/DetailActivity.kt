package com.example.guide

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        val webView: WebView = findViewById(R.id.webView)
        val intent = intent
        val resName = "n" + intent.getIntExtra("title", 0)
        val context: Context = baseContext
        val text: String = readRawTextFile(
            context, resources.getIdentifier(
                resName,
                "raw", "com.example.guide"
            )
        )
        webView.loadDataWithBaseURL(null, text, "text/html", "en_US", null)
    }

    private fun readRawTextFile(context: Context, resId: Int): String {
        val inputStream: InputStream = context.resources.openRawResource(resId)
        val inputReader = InputStreamReader(inputStream)
        val buffReader = BufferedReader(inputReader)
        var line: String?
        val builder = StringBuilder()
        try {
            while (buffReader.readLine().also { line = it } != null) {
                builder.append(line)
                builder.append("\n")
            }
        } catch (e: IOException) {
            return e.localizedMessage
        }
        return builder.toString()
    }
}
