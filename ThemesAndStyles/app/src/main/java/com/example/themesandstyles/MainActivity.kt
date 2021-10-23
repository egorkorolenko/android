package com.example.themesandstyles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        }

        val button2: Button = findViewById(R.id.button2)

        button2.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)
        }

        val send:Button = findViewById(R.id.send)

        send.setOnClickListener{
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            val username: EditText = findViewById(R.id.editUserName)
            val gifts: EditText = findViewById(R.id.editGifts)
            intent.putExtra("username",username.text.toString())
            intent.putExtra("gift",gifts.text.toString())
            startActivity(intent)
        }
    }
}