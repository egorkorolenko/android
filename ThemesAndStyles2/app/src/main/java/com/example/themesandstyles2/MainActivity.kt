package com.example.themesandstyles2

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val button: Button = findViewById(R.id.button);
        onClick(null)
    }

    private fun onClick(view: View?) {
        val builder: AlertDialog.Builder =
            AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
        builder.setTitle("Dialog")
        builder.setMessage("Покормить кота?")
        builder.setPositiveButton("OK", null)
        builder.setNegativeButton("НЕТ", null)
        builder.show()
    }
}