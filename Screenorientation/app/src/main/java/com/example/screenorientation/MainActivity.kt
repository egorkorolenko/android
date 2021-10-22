package com.example.screenorientation

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Surface

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getScreenOrientation(): String {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> "Портретная ориентация"
            Configuration.ORIENTATION_LANDSCAPE -> "Альбомная ориентация"
            else -> ""
        }
    }

    private fun getRotateOrientation(): String {
        return when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> "Не поворачивали"
            Surface.ROTATION_90 -> "Повернули на 90 градусов по часовой стрелке"
            Surface.ROTATION_180 -> "Повернули на 180 градусов"
            Surface.ROTATION_270 -> "Повернули на 90 градусов против часовой стрелки"
            else -> "Не понятно"
        }
    }
}