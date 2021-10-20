package com.example.switchingbetweenapplicationscreens

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second2)
        val text: TextView = findViewById(R.id.mess)

        val user = intent.extras?.getString("username")
        val gifts = intent.extras?.getString("gift")

        text.text = "$user передал вам $gifts"

    }
}