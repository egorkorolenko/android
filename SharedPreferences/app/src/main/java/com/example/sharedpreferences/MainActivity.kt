package com.example.sharedpreferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val APP_PREFERENCES_COUNTER = "counter"
    private var counter:Int = 0
    private lateinit var prefs: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs= getSharedPreferences("settings",Context.MODE_PRIVATE)

        val textView: TextView = findViewById(R.id.textView)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener{
            textView.text = "Иди своей дорогой, сталкер!"
        }

        val buttonCounter: Button = findViewById(R.id.button_counter)

        buttonCounter.setOnClickListener{
            textView.text = "Я насчитал ${++counter} ворон"
        }

        val clear:Button = findViewById(R.id.button_delete)
        clear.setOnClickListener{
            counter = 0
            textView.text = "Я насчитал 0 ворон"
        }
    }

    override fun onPause() {
        super.onPause()

        val editor = prefs.edit()
        editor.putInt(APP_PREFERENCES_COUNTER, counter).apply()
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        val textView: TextView = findViewById(R.id.textView)
        super.onResume()
        if(prefs.contains(APP_PREFERENCES_COUNTER)){
            counter = prefs.getInt(APP_PREFERENCES_COUNTER,0)
            textView.text = "Я насчитал $counter ворон"
        }
    }
}