package com.example.simplepaint

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val draw2D = Draw2D(this)
        setContentView(draw2D)
    }
}