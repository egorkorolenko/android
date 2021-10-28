package com.example.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.provider.SettingsSlicesContract
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.view.WindowMetrics
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seekBar:SeekBar = findViewById(R.id.seekBar)
        val textView: TextView = findViewById(R.id.textView)
        val brightness = getBrightness()
        seekBar.progress = brightness

        textView.text = "Screen Brightness: $brightness"

        val canWrite = Settings.System.canWrite(this)

        if (!canWrite) {
            seekBar.isEnabled = false
            allowWritePermission()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = "Screen Brightness : $progress"
                setBrightness(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { }

            override fun onStopTrackingTouch(seekBar: SeekBar) { }
        })

        screenSize()
    }

    @SuppressLint("SetTextI18n")
    fun screenSize(){
        val size: TextView = findViewById(R.id.text)

        val display: Display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val screenWidth: Int = point.x
        val screenHeight: Int = point.y

        val width = Integer.toString(screenWidth)
        val height = Integer.toString(screenHeight)

        size.text = "Ширина: $width; Высота: $height"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun allowWritePermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_WRITE_SETTINGS,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }

    private fun getBrightness(): Int {
        return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
    }

    fun setBrightness(value: Int) {
        Settings.System.putInt(
            this.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            value
        )
    }
}