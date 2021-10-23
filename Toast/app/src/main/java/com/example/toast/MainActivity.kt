package com.example.toast

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = "Пора покормить кота!"
        val duration = Toast.LENGTH_LONG

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val toast = Toast.makeText(applicationContext, text, duration)
            val toastContainer = toast.view as LinearLayout
            val catImage = ImageView(this)
            catImage.setImageResource(R.drawable.cat)
            toastContainer.addView(catImage, 0)
            toast.show()

        }
    }
}