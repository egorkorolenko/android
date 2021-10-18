package com.example.theravencounter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var counter:Int = 0
    private var counterCats:Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener{
            textView.text = "Иди своей дорогой, сталкер!"
        }

        val buttonCounter: Button = findViewById(R.id.button_counter)

        buttonCounter.setOnClickListener{
            textView.text = "Я насчитал ${counter++} ворон"
        }

        val buttonCounterCats: Button = findViewById(R.id.button_counter_cats)

        buttonCounterCats.setOnClickListener{
            textView.text = "Я насчитал ${counterCats++} котов"
        }
    }
}