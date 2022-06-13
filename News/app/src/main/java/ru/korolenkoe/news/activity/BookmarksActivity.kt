package ru.korolenkoe.news.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.korolenkoe.news.R
import ru.korolenkoe.news.adapter.NewsAdapter
import ru.korolenkoe.news.db.UserDatabase
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.UserModel
import ru.korolenkoe.news.repository.UserRepository

class BookmarksActivity :AppCompatActivity() {

    private var articlesArrayList: ArrayList<Articles> = arrayListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var toolBar: Toolbar
    private lateinit var recyclerViewNews: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var userModel:UserModel

    private lateinit var database: UserDatabase
    private lateinit var repository: UserRepository

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bookmarks_fragment)

        database = UserDatabase.getDatabase(this.application)
        repository = UserRepository(database.userDao())
        val argument: Bundle? = intent.extras

        val login = argument?.get("login").toString()
        if (login != "null") {
            userModel = getUserByLogin(login)
        }
        articlesArrayList = userModel.bookMarks as ArrayList<Articles>
        progressBar = findViewById(R.id.progressBarBookmarks)
        toolBar = findViewById(R.id.toolbar_title_bookmarks)
        recyclerViewNews = findViewById(R.id.recyclerViewBookmarks)
        swipeRefreshLayout = findViewById(R.id.id_swipe_refreshBookmarks)
        newsAdapter = NewsAdapter(articlesArrayList, this, null, null)

        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.adapter = newsAdapter
        newsAdapter.notifyDataSetChanged()
    }

    private fun getUserByLogin(login: String): UserModel {
        return repository.getUserByLogin(login)
    }

}