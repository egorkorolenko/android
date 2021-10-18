package com.example.trafficlights

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootLayout: ConstraintLayout = findViewById(R.id.root_layout)
        val textView:TextView = findViewById(R.id.textView)
        val redButton: Button = findViewById(R.id.buttonRed)
        val yellowButton: Button = findViewById(R.id.buttonYellow)
        val greenButton: Button = findViewById(R.id.buttonGreen)

        redButton.setOnClickListener{
            textView.text = "Red"
            rootLayout.setBackgroundColor(resources.getColor(R.color.redColor,null))
        }

        yellowButton.setOnClickListener{
            textView.text = "Yellow"
            rootLayout.setBackgroundColor(resources.getColor(R.color.yellowColor,null))
        }

        greenButton.setOnClickListener{
            textView.text = "Green"
            rootLayout.setBackgroundColor(resources.getColor(R.color.greenColor,null))
        }
    }
}