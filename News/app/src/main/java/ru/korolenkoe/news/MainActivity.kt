package ru.korolenkoe.news

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val timeNow: TextView = findViewById(R.id.time)
        val timeNow2: TextView = findViewById(R.id.time_correct)
        val dateToday: TextView = findViewById(R.id.date)
        val dateToday2: TextView = findViewById(R.id.date_correct)

        var monthRus = ""
        when (month) {
            0 -> monthRus = "Января"
            1 -> monthRus = "Февраля"
            2 -> monthRus = "Марта"
            3 -> monthRus = "Апреля"
            4 -> monthRus = "Мая"
            5 -> monthRus = "Июня"
            6 -> monthRus = "Июля"
            7 -> monthRus = "Августа"
            8 -> monthRus = "Сентября"
            9 -> monthRus = "Октября"
            10 -> monthRus = "Ноября"
            11 -> monthRus = "Декабря"
        }

        timeNow.text = "Сейчас:"
        dateToday.text = "Сегодня:"

        timeNow2.text = "$hour:$minute"
        dateToday2.text = "$day $monthRus, $year"

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(intent)
            finish()
        },3000)

    }
}