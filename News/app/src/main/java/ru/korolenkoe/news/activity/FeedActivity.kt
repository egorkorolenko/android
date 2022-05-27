package ru.korolenkoe.news.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.korolenkoe.news.ClickCategoryInterface
import ru.korolenkoe.news.R
import ru.korolenkoe.news.RetrofitAPI
import ru.korolenkoe.news.adapter.CategoryAdapter
import ru.korolenkoe.news.adapter.NewsAdapter
import ru.korolenkoe.news.fragments.BookmarksFragment
import ru.korolenkoe.news.fragments.DownloadFragment
import ru.korolenkoe.news.fragments.ProfileFragment
import ru.korolenkoe.news.fragments.SettingsFragment
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.CategoryModel
import ru.korolenkoe.news.model.NewsModel
import kotlin.system.exitProcess


//ed7b9a5f85274d88ac578e199f7cf65e
//7f48007fe08247348150f6d0df56beef
class FeedActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
    private lateinit var openMenu:ImageView
    private lateinit var driverLayout: DrawerLayout

    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var signInButton:Button

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        progressBar = findViewById(R.id.progressBar)
        driverLayout = findViewById(R.id.driverLayout)
        openMenu = findViewById(R.id.image_open_menu)
        swipeRefreshLayout = findViewById(R.id.id_swipe_refresh)
        toolBar = findViewById(R.id.toolbar)
        recyclerViewCategory = findViewById(R.id.recyclerViewCategory)
        recyclerViewNews = findViewById(R.id.recyclerViewNews)
        searchImage = findViewById(R.id.search_image_view)
        signInButton = findViewById(R.id.sing_in_nav)

        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController =  navHostFragment.navController
//        navigationView.setupWithNavController(navController)

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
        getNews("Всё")

        newsAdapter.notifyDataSetChanged()
        categoryAdapter.notifyItemChanged(0, categorys.size)
        searchImage.setOnClickListener {
            val intent = Intent(this@FeedActivity, SearchNewsByQ::class.java)
            startActivity(intent)
        }
        openMenu.setOnClickListener {
            driverLayout.openDrawer(GravityCompat.START)
        }

        signInButton.setOnClickListener {
                val intent = Intent(this@FeedActivity, LoginActivity::class.java)
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
        categorys.add(CategoryModel("+ своя"))
        categoryAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addCategory() {
        val li:LayoutInflater = LayoutInflater.from(this)
        val promt : View = li.inflate(R.layout.promt_add_category,null)
        val input : EditText = promt.findViewById(R.id.input_category)
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.MyDialogTheme)
        alertDialogBuilder.setView(promt)
        alertDialogBuilder.setPositiveButton("Готово") { _, _ ->
            if(input.text.toString()!=""){
                categorys.removeAt(categorys.size-1)
                categorys.add(CategoryModel(input.text.toString()))
                categorys.add(CategoryModel("+ своя"))
                categoryAdapter.notifyDataSetChanged()
            }
           }
        alertDialogBuilder.setNegativeButton("Отмена") { _, _ ->
        }
        val alertDialogCreated:AlertDialog = alertDialogBuilder.create();
        alertDialogCreated.show()
        articlesArrayList.clear()
    }

    private fun getNews(category: String) {
        progressBar.visibility = View.VISIBLE

        var url="https://newsapi.org/v2/top-headlines?country=ru&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
        val categoryUrl: String

        if(category == "+ своя"){
            addCategory()
        }

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
                null
            }
        }

        articlesArrayList.clear()

        if(categoryEn == null){
            categoryUrl = if(category != "+ своя"){
                "https://newsapi.org/v2/everything?q=$category&from=2022-05-14&sortBy=publishedAt&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
//                "https://newsapi.org/v2/everything?q=$category&sortBy=publishedAt&apiKey=7f48007fe08247348150f6d0df56beef"
            }else {
                "https://newsapi.org/v2/top-headlines?country=ru&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
            }
        }else{
            categoryUrl =
                "https://newsapi.org/v2/top-headlines?country=ru&category=$categoryEn&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
            url =
                "https://newsapi.org/v2/top-headlines?country=ru&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
        }

        val baseUrl = "https://newsapi.org/"

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment?
        when (item.itemId) {
            R.id.profileMenu -> {
                fragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(R.id.driverLayout, fragment).commit()
            }
            R.id.download -> {
                fragment = DownloadFragment()
                supportFragmentManager.beginTransaction().replace(R.id.driverLayout, fragment).commit()
            }
            R.id.settings -> {
                fragment = SettingsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.driverLayout, fragment).commit()
            }
            R.id.bookmarks -> {
                fragment = BookmarksFragment()
                supportFragmentManager.beginTransaction().replace(R.id.driverLayout, fragment).commit()
            }
        }

        driverLayout.closeDrawer(GravityCompat.START)
        onStop()
        return true
        }

    override fun onBackPressed() {
        if(supportFragmentManager.isStateSaved){
            val intent = Intent(this,FeedActivity::class.java)
            startActivity(intent)
        }else{
//            supportFragmentManager.popBackStack()
//            supportFragmentManager.beginTransaction().remove(ProfileFragment()).commit()
//            supportFragmentManager.beginTransaction().remove(BookmarksFragment()).commit()
//            supportFragmentManager.beginTransaction().remove(SettingsFragment()).commit()
//            supportFragmentManager.beginTransaction().remove(DownloadFragment()).commit()
            onStop()
            exitProcess(0)
        }
//        val drawer = findViewById<View>(R.id.driverLayout) as DrawerLayout
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
    }
}