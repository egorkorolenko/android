package com.example.sherlock

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CHOOSE_THIEF = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button_choose)

        button.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            startActivityForResult(intent, REQUEST_CHOOSE_THIEF)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val textview_info:TextView = findViewById(R.id.textview_into)

        if (requestCode == REQUEST_CHOOSE_THIEF) {
            if (resultCode == Activity.RESULT_OK) {
                val thiefName = data?.getStringExtra(THIEF)
                textview_info.text = thiefName
            } else {
                textview_info.text = ""
            }
        }
    }
}