package com.example.dialogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button:Button = findViewById(R.id.button)
        button.setOnClickListener {
            val myDialogFragment = MyDialogFragment()
            val manager = supportFragmentManager
            myDialogFragment.show(manager,"MyDialog")
        }
    }

    fun okClicked() {
        Toast.makeText(
            applicationContext, "Вы выбрали кнопку OK!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun cancelClicked() {
        Toast.makeText(
            applicationContext, "Вы выбрали кнопку отмены!",
            Toast.LENGTH_LONG
        ).show()
    }
}