package com.example.preferencesframework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.content.SharedPreferences
import android.widget.TextView
import androidx.preference.PreferenceManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button= findViewById(R.id.button)
        button.setOnClickListener {
            showSettings()
        }

        val button2: Button= findViewById(R.id.button2)
        button2.setOnClickListener {
            ringtone()
        }

        val button3: Button= findViewById(R.id.button3)
        button3.setOnClickListener {
            category()
        }

    }

    private fun category() {
        val intent = Intent(this, Category::class.java)
        startActivity(intent)
    }

    private fun ringtone() {
        val intent = Intent(this, RingtonePreferenceActivity::class.java)
        startActivity(intent)
    }

    private fun showSettings() {
        val intent = Intent(this, MyPreferenceActivity::class.java)
        startActivity(intent)
    }
}