package ru.korolenkoe.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchNewsByQ : AppCompatActivity() {

    private var articlesArrayList: ArrayList<Articles> = arrayListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var toolBar: Toolbar
    private lateinit var recyclerViewNews: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var editText: EditText
    private lateinit var searchView: SearchView
    private lateinit var q: String


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_news)

        progressBar = findViewById(R.id.progressBarSearch)
        toolBar = findViewById(R.id.toolbarSearch)
        swipeRefreshLayout = findViewById(R.id.id_swipe_refreshSearch)
        recyclerViewNews = findViewById(R.id.recyclerViewSearchNews)
        searchView = findViewById(R.id.searchViewId)
        newsAdapter = NewsAdapter(articlesArrayList, this)
        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.adapter = newsAdapter
        newsAdapter.notifyDataSetChanged()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                articlesArrayList.clear()
                q = query.toString()
                getNewsByQ(q)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                q = newText.toString()
                getNewsByQ(q)
                return false
            }
        })
    }

    private fun getNewsByQ(request: String) {

        articlesArrayList.clear()

        val responce =
            "https://newsapi.org/v2/everything?q=$request&from=2022-01-26&sortBy=publishedAt&apiKey=7f48007fe08247348150f6d0df56beef"
        val baseUrl = "https://newsapi.org/"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitApi: RetrofitAPI = retrofit.create(RetrofitAPI::class.java)

        val call: Call<NewsModel> = retrofitApi.getNewsByQuestion(responce)

        call.enqueue(object : Callback<NewsModel> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                progressBar.visibility = View.GONE
                val newsModel = response.body()
                if (newsModel != null) {
                    if (newsModel.articles != null) {
                        val articles: ArrayList<Articles> = newsModel!!.articles
                        for (i in articles) {
                            val article = Articles(
                                i.title,
                                i.description,
                                i.urlToImage,
                                i.url,
                                i.content,
                                i.publishedAt
                            )
                            articlesArrayList.add(article)
                        }
                    }
                }
                newsAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                val toast =
                    Toast.makeText(this@SearchNewsByQ, "Fail get news", Toast.LENGTH_SHORT)
                toast.show()
            }
        })

    }
}