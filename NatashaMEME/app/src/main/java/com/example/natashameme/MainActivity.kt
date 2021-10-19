package com.example.natashameme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val secondText: TextView = findViewById(R.id.textView)
        val thirdText: TextView = findViewById(R.id.textView2)
        val forthText: TextView = findViewById(R.id.textView3)
        val rightBottomImage: ImageView = findViewById(R.id.cat)

        rightBottomImage.setOnClickListener {
            val phrases = listOf(
                "Meow",
                "Meow, Meow, Meow",
                "Meeeeeeeeow",
                "Mew",
                "Meoow",
                "Mw",
                "мяуу",
                "M"
                )

            val shuffledList = phrases.shuffled()

            secondText.text = shuffledList[0]
            thirdText.text = shuffledList[1]
            forthText.text = shuffledList[2]
        }
    }}