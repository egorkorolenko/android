package com.example.popupmenu

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView: ImageView = findViewById(R.id.imageView)
        val textView: TextView = findViewById(R.id.textView)
        val button: Button = findViewById(R.id.button)

        val popupMenu = androidx.appcompat.widget.PopupMenu(this, imageView)
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu -> {
                    textView.text = "Вы выбрали PopupMenu 1"
                    true
                }
                R.id.menu2 -> {
                    textView.text = "Вы выбрали PopupMenu 2"
                    true
                }
                R.id.menu3 -> {
                    textView.text = "Вы выбрали PopupMenu 3"
                    true
                }
                else -> false
            }
        }

        imageView.setOnClickListener {
            popupMenu.show()
        }

        val popupMenu2 = PopupMenu(this, button)
        popupMenu2.inflate(R.menu.popup_menu)
        popupMenu2.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.red -> {
                    textView.background = ColorDrawable(Color.RED)
                    textView.text = "Вы выбрали красный цвет"
                }
                R.id.yellow -> {
                    textView.background = ColorDrawable(Color.YELLOW)
                    textView.text = "Вы выбрали жёлтый цвет"
                }
                R.id.green -> {
                    textView.background = ColorDrawable(Color.GREEN)
                    textView.text = "Вы выбрали зелёный цвет"
                }
            }
            false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu2.setForceShowIcon(true)
        }

        button.setOnClickListener {
            popupMenu2.show()
        }
    }
}