package ru.korolenkoe.news.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import ru.korolenkoe.news.R

class OpenNews : AppCompatActivity() {

    private lateinit var contentTV: TextView
    private lateinit var descTV: TextView
    private lateinit var titleTV: TextView
    private lateinit var imageIV: ImageView
    private lateinit var bottom: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_news)
        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")
        val content = intent.getStringExtra("content")
        val image = intent.getStringExtra("image")
        val url = intent.getStringExtra("url")

        contentTV = findViewById(R.id.idContent)
        descTV = findViewById(R.id.idDescription)
        titleTV = findViewById(R.id.idTitle)
        imageIV = findViewById(R.id.idNewsImage)
        bottom = findViewById(R.id.idbottomReadNews)

        titleTV.text = title
        descTV.text = desc
        contentTV.text = content
        Picasso.get().load(image).into(imageIV)
        bottom.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}