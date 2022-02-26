package ru.korolenkoe.news

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
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


//ed7b9a5f85274d88ac578e199f7cf65e
//7f48007fe08247348150f6d0df56beef
class FeedActivity : AppCompatActivity() {

    private var categorys: ArrayList<CategoryModel> = arrayListOf()
    private var articlesArrayList: ArrayList<Articles> = arrayListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var toolBar: Toolbar
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var recyclerViewNews: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchImage:ImageView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        progressBar = findViewById(R.id.progressBar)
        swipeRefreshLayout = findViewById(R.id.id_swipe_refresh)
        toolBar = findViewById(R.id.toolbar)
        recyclerViewCategory = findViewById(R.id.recyclerViewCategory)
        recyclerViewNews = findViewById(R.id.recyclerViewNews)
        searchImage = findViewById(R.id.search_image_view)
        categoryAdapter = CategoryAdapter(categorys, this, object : ClickCategoryInterface {
            override fun onClickCategory(position: Int) {
                val category: String = categorys[position].category
                getNews(category)
            }
        })
        newsAdapter = NewsAdapter(articlesArrayList, this)

        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.adapter = newsAdapter
        recyclerViewCategory.adapter = categoryAdapter

        getCategories()
        getNews("All")

        newsAdapter.notifyDataSetChanged()
        categoryAdapter.notifyItemChanged(0, categorys.size)
        searchImage.setOnClickListener {
            val intent = Intent(this@FeedActivity,SearchNewsByQ::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getCategories() {
        categorys.add(CategoryModel("Всё"))
        categorys.add(CategoryModel("Главное"))
        categorys.add(CategoryModel("Бизнес"))
        categorys.add(CategoryModel("Развлечение"))
        categorys.add(CategoryModel("Здоровье"))
        categorys.add(CategoryModel("Наука"))
        categorys.add(CategoryModel("Спорт"))
        categorys.add(CategoryModel("Технологии"))
        categoryAdapter.notifyDataSetChanged()
    }


    private fun getNews(category: String) {
        progressBar.visibility = View.VISIBLE

        val categoryEn = when (category) {
            "Главное" -> "general"
            "Всё" -> "All"
            "Бизнес" -> "business"
            "Развлечение" -> "entertainment"
            "Здоровье" -> "health"
            "Наука" -> "science"
            "Спорт" -> "sports"
            "Технологии" -> "technology"
            else -> {
                "All"
            }
        }

        articlesArrayList.clear()
        val categoryUrl =
            "https://newsapi.org/v2/top-headlines?country=ru&category=$categoryEn&apiKey=7f48007fe08247348150f6d0df56beef"
        val url =
            "https://newsapi.org/v2/top-headlines?country=ru&apiKey=7f48007fe08247348150f6d0df56beef"
        val baseUrl = "https://newsapi.org/"
        val customCategory =
            "https://newsapi.org/v2/everything?q=путин&sortBy=publishedAt&apiKey=7f48007fe08247348150f6d0df56beef"


        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitApi: RetrofitAPI = retrofit.create(RetrofitAPI::class.java)

        val call: Call<NewsModel> = if (categoryEn == "All") {
            retrofitApi.getAllNews(url)
        } else {
            retrofitApi.getNewsByCategory(categoryUrl)
        }

        call.enqueue(object : Callback<NewsModel> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                progressBar.visibility = View.GONE
                val newsModel = response.body()
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
                newsAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                val toast =
                    Toast.makeText(this@FeedActivity, "Fail get news", Toast.LENGTH_SHORT)
                toast.show()
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            articlesArrayList.clear()

            val call: Call<NewsModel> = if (categoryEn == "All") {
                retrofitApi.getAllNews(url)
            } else {
                retrofitApi.getNewsByCategory(categoryUrl)
            }

            call.enqueue(object : Callback<NewsModel> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                    progressBar.visibility = View.GONE
                    val newsModel = response.body()
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
                    newsAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                    val toast =
                        Toast.makeText(this@FeedActivity, "Fail get news", Toast.LENGTH_SHORT)
                    toast.show()
                }
            })
            swipeRefreshLayout.isRefreshing = false
        }
    }
}