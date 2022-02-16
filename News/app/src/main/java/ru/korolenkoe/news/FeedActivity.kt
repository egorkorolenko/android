package ru.korolenkoe.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//ed7b9a5f85274d88ac578e199f7cf65e

class FeedActivity :AppCompatActivity(), ClickCategoryInterface {

    private var categorys:ArrayList<CategoryModel> = arrayListOf()
    private var articlesArrayList : ArrayList<Articles> = arrayListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var toolBar: Toolbar
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var recyclerViewNews : RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        progressBar = findViewById(R.id.progressBar)
        toolBar = findViewById(R.id.toolbar)
        recyclerViewCategory = findViewById(R.id.recyclerViewCategory)
        recyclerViewNews = findViewById(R.id.recyclerViewNews)
        categoryAdapter = CategoryAdapter(categorys,this,null)
        newsAdapter = NewsAdapter(articlesArrayList,this)

        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.adapter = newsAdapter
        recyclerViewCategory.adapter = categoryAdapter

        supportActionBar?.hide()
        setSupportActionBar(toolBar)

        getCategories()
        getNews("All")
        newsAdapter.notifyDataSetChanged()
    }

    override fun onClickCategory(position: Int) {
        val category: String = categorys[position].category
        getNews(category)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getCategories(){
        categorys.add(CategoryModel("All","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1169&q=80"))
        categorys.add(CategoryModel("All","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1169&q=80"))
        categorys.add(CategoryModel("All","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1169&q=80"))
        categorys.add(CategoryModel("All","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1169&q=80"))
        categorys.add(CategoryModel("All","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1169&q=80"))
        categorys.add(CategoryModel("All","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1169&q=80"))
        categorys.add(CategoryModel("All","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1169&q=80"))
        categorys.add(CategoryModel("All","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1169&q=80"))
        categoryAdapter.notifyDataSetChanged()
    }

    private fun getNews(category:String){
//        progressBar.visibility = View.VISIBLE
        articlesArrayList.clear()
        val categoryUrl =
            "https://newsapi.org/v2/top-headlines/country=ru&category=$category&apikey=ed7b9a5f85274d88ac578e199f7cf65e"
        val url = "https://newsapi.org/v2/top-headlines?country=ru&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
        val baseUrl = "https://newsapi.org/"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitApi:RetrofitAPI = retrofit.create(RetrofitAPI::class.java)


        val call:Call<NewsModel> = if(category == "All"){
            retrofitApi.getAllNews(url)
        }else{
            retrofitApi.getNewsByCategory(categoryUrl)
        }

        call.enqueue(object : Callback<NewsModel>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                val newsModel = response.body()
                toolBar.visibility = View.GONE
//                progressBar.visibility = View.GONE
                val articles: ArrayList<Articles> = newsModel!!.articles
                for(i in articles){
                    val article = Articles(i.title,i.description,i.urlToImage,i.url,i.content)
                    articlesArrayList.add(article)
                }
                newsAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                val toast = Toast.makeText(this@FeedActivity, "Fail get news", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }
}