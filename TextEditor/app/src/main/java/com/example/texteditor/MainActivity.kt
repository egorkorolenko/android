package com.example.texteditor

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import java.io.File

class MainActivity : AppCompatActivity() {

    private val FILENAME = "sample.txt"

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_open -> {
                openFile(FILENAME)
                true
            }
            R.id.action_save -> {
                saveFile(FILENAME)
                true
            }
            R.id.action_settings -> {
                val intent = Intent()
                intent.setClass(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    override fun onResume() {
        super.onResume()
        val prefs = PreferenceManager
            .getDefaultSharedPreferences(this)
        if (prefs.getBoolean(getString(R.string.pref_openmode), false)) {
            openFile(FILENAME)
        }

        val regular = prefs.getString(getString(R.string.pref_style), "")
        var typeface = Typeface.NORMAL
        if (regular!!.contains("Полужирный")) typeface += Typeface.BOLD
        if (regular.contains("Курсив")) typeface += Typeface.ITALIC
        editText.setTypeface(null, typeface)
    }

    private fun openFile(fileName: String) {

        val textFromFile =
            File(applicationContext.filesDir, fileName)
                .bufferedReader()
                .use { it.readText(); }
        editText.setText(textFromFile)
    }

    private fun saveFile(fileName: String) {
        applicationContext.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(editText.text.toString().toByteArray())
        }
        Toast.makeText(applicationContext, "Файл сохранён", Toast.LENGTH_SHORT).show()
    }
}