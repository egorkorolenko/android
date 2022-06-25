package ru.korolenkoe.news.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ru.korolenkoe.news.R
import ru.korolenkoe.news.adapter.DownloadAdapter
import ru.korolenkoe.news.utils.ClickCategoryInterface
import java.io.File


class DownloadActivity : AppCompatActivity() {

    private var filesArray: Array<String?>? = null
    private lateinit var newsAdapter: DownloadAdapter
    private lateinit var recyclerViewNews: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.download_fragment)
        viewAllDownloads()

        recyclerViewNews = findViewById(R.id.recyclerViewDownloads)
        newsAdapter = DownloadAdapter(filesArray, this, object : ClickCategoryInterface {
            override fun onClickCategory(position: Int) {
                val category: String = filesArray?.get(position).toString()
            }
        })
        recyclerViewNews.adapter = newsAdapter
        newsAdapter.notifyItemChanged(0, filesArray?.size)

    }

    private fun viewAllDownloads() {
        val files = File("/sdcard/News").listFiles()
        val fileNames = arrayOfNulls<String>(files.size)
        files?.mapIndexed { index, item ->
            fileNames[index] = item?.name
        }
        filesArray = fileNames
    }
}