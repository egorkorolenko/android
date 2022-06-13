package ru.korolenkoe.news.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
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
import ru.korolenkoe.news.R
import ru.korolenkoe.news.adapter.CategoryAdapter
import ru.korolenkoe.news.adapter.NewsAdapter
import ru.korolenkoe.news.db.UserDBWork
import ru.korolenkoe.news.db.UserDatabase
import ru.korolenkoe.news.fragments.BookmarksFragment
import ru.korolenkoe.news.fragments.DownloadFragment
import ru.korolenkoe.news.fragments.ProfileFragment
import ru.korolenkoe.news.fragments.SettingsFragment
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.CategoryModel
import ru.korolenkoe.news.model.NewsModel
import ru.korolenkoe.news.model.UserModel
import ru.korolenkoe.news.repository.UserRepository
import ru.korolenkoe.news.utils.CheckInternetConnection
import ru.korolenkoe.news.utils.ClickCategoryInterface
import ru.korolenkoe.news.utils.RetrofitAPI


//ed7b9a5f85274d88ac578e199f7cf65e
//7f48007fe08247348150f6d0df56beef
class FeedActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var categories: ArrayList<CategoryModel> = arrayListOf()
    private var articlesArrayList: ArrayList<Articles> = arrayListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var toolBar: Toolbar
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var recyclerViewNews: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchImage: ImageView
    private lateinit var openMenu: ImageView
    private lateinit var driverLayout: DrawerLayout
    private var disconnected: Boolean = false
    private lateinit var checkNetworkConnection: CheckInternetConnection

    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var userNameNavigationView: TextView
    private lateinit var database: UserDatabase
    private lateinit var repository: UserRepository
    private var userModel: UserModel = UserModel(-1, "", "Гость", "", "", listOf(), listOf())
    private var isLogin = false

    private val APP_PREFERENCES = "userlogin"
    private var USERLOGIN = ""
    private lateinit var prefs: SharedPreferences

    private lateinit var signInButton: Button

    @SuppressLint("NotifyDataSetChanged", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        progressBar = findViewById(R.id.progressBar)
        driverLayout = findViewById(R.id.driverLayout)
        openMenu = findViewById(R.id.image_open_menu)
        swipeRefreshLayout = findViewById(R.id.id_swipe_refresh)

        database = UserDatabase.getDatabase(this.application)
        repository = UserRepository(database.userDao())

        toolBar = findViewById(R.id.toolbar)
        recyclerViewCategory = findViewById(R.id.recyclerViewCategory)
        recyclerViewNews = findViewById(R.id.recyclerViewNews)
        searchImage = findViewById(R.id.search_image_view)
        signInButton = findViewById(R.id.sing_in_nav)

        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        val headerContainer: View = navigationView.getHeaderView(0)
        userNameNavigationView = headerContainer.findViewById(R.id.userNameNV)

        val argument: Bundle? = intent.extras
        val login = argument?.get("login").toString()
        if (login != "null") {
            USERLOGIN = login
            userModel = getUserByLogin(login)
            isLogin = true
            navUpdate()
        }

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        categoryAdapter = CategoryAdapter(categories, this, object : ClickCategoryInterface {
            override fun onClickCategory(position: Int) {
                val category: String = categories[position].category
                getNews(category)
            }
        })
        newsAdapter = NewsAdapter(articlesArrayList, this, database, userModel)

        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.adapter = newsAdapter
        recyclerViewCategory.adapter = categoryAdapter

        getNews("Всё")

        newsAdapter.notifyDataSetChanged()
        categoryAdapter.notifyItemChanged(0, categories.size)
        searchImage.setOnClickListener {
            val intent = Intent(this@FeedActivity, SearchNewsActivity::class.java)
            startActivity(intent)
        }
        openMenu.setOnClickListener {
            driverLayout.openDrawer(GravityCompat.START)
        }

        signInButton.setOnClickListener {
            if (signInButton.text.toString() == "Выйти") {
                logout()
            } else {
                val intent = Intent(this@FeedActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        callNetworkConnection()
        prefs = getSharedPreferences(
            APP_PREFERENCES, Context.MODE_PRIVATE
        )
    }


    private fun navUpdate() {
        if (isLogin) {
            signInButton.text = "Выйти"
            userNameNavigationView.text = userModel.name
        }
    }

    private fun getUserByLogin(login: String): UserModel {
        return repository.getUserByLogin(login)
    }

    private fun logout() {
        val li: LayoutInflater = LayoutInflater.from(this)
        val promt: View = li.inflate(R.layout.promt_logout, null)
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.MyDialogTheme)
        alertDialogBuilder.setView(promt)
        alertDialogBuilder.setPositiveButton("Да") { _, _ ->
            signInButton.text = "Войти"
            USERLOGIN = ""
            userNameNavigationView.text = "Гость"
            userModel = UserModel(-1, "", "Гость", "", "", listOf(), listOf())
            isLogin = false
            getCategories()
            prefs.edit().remove(APP_PREFERENCES).apply()
        }
        alertDialogBuilder.setNegativeButton("Отмена") { _, _ ->
        }
        val alertDialogCreated: AlertDialog = alertDialogBuilder.create();
        alertDialogCreated.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getCategories() {
        if (!isLogin) {
            categories.add(CategoryModel("Всё"))
            categories.add(CategoryModel("Главное"))
            categories.add(CategoryModel("Бизнес"))
            categories.add(CategoryModel("Развлечение"))
            categories.add(CategoryModel("Здоровье"))
            categories.add(CategoryModel("Наука"))
            categories.add(CategoryModel("Спорт"))
            categories.add(CategoryModel("Технологии"))
            categories.add(CategoryModel("+ своя"))
        } else {
            for (category in userModel.categories) {
                categories.add(CategoryModel(category))
            }
        }
        categoryAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addCategory() {
        val li: LayoutInflater = LayoutInflater.from(this)
        val promt: View = li.inflate(R.layout.promt_add_category, null)
        val input: EditText = promt.findViewById(R.id.input_category)
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.MyDialogTheme)
        alertDialogBuilder.setView(promt)
        alertDialogBuilder.setPositiveButton("Готово") { _, _ ->
            if (input.text.toString() != "" && !checkUniqueCategory(input.text.toString())) {
                categories.removeAt(categories.size - 1)
                categories.add(CategoryModel(input.text.toString()))
                categories.add(CategoryModel("+ своя"))
                categoryAdapter.notifyDataSetChanged()
                if(userModel.login!=""){
                    val userDBWork = UserDBWork()
                    userDBWork.addCategory(userModel,input.text.toString(), database)
                }
            }
        }
        alertDialogBuilder.setNegativeButton("Отмена") { _, _ ->
        }
        val alertDialogCreated: AlertDialog = alertDialogBuilder.create();
        alertDialogCreated.show()
        articlesArrayList.clear()
    }

    fun checkUniqueCategory(category: String):Boolean{
        var isRepeat = false
        for(categoryNew in categories){
            if(category ==categoryNew.category){
                isRepeat = true
                break
            }
        }
        return isRepeat
    }
    private fun getNews(category: String) {
        progressBar.visibility = View.VISIBLE

        var url =
            "https://newsapi.org/v2/top-headlines?country=ru&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
        val categoryUrl: String

        if (category == "+ своя") {
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

        if (categoryEn == null) {
            categoryUrl = if (category != "+ своя") {
                "https://newsapi.org/v2/everything?q=$category&from=2022-05-14&sortBy=publishedAt&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
//                "https://newsapi.org/v2/everything?q=$category&sortBy=publishedAt&apiKey=7f48007fe08247348150f6d0df56beef"
            } else {
                "https://newsapi.org/v2/top-headlines?country=ru&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
            }
        } else {
            categoryUrl =
                "https://newsapi.org/v2/top-headlines?country=ru&category=$categoryEn&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
            url =
                "https://newsapi.org/v2/top-headlines?country=ru&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
        }

        val baseUrl = "https://newsapi.org/"

        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()

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
                        i.title, i.description, i.urlToImage, i.url, i.content, i.publishedAt
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
                if (!isLogin) {
                    Toast.makeText(
                        this@FeedActivity,
                        "Не выполнен вход в аккаунт",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.driverLayout, fragment)
                        .commit()
                }
            }
            R.id.download -> {
                if (!isLogin) {
                    Toast.makeText(
                        this@FeedActivity,
                        "Не выполнен вход в аккаунт",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    fragment = DownloadFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.driverLayout, fragment)
                        .commit()
                }
            }
            R.id.settings -> {
                fragment = SettingsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.driverLayout, fragment)
                    .commit()
            }
            R.id.bookmarks -> {
                if (!isLogin) {
                    Toast.makeText(
                        this@FeedActivity,
                        "Не выполнен вход в аккаунт",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    fragment = BookmarksFragment()
//                    val intent2 = Intent(this@FeedActivity,BookmarksActivity::class.java)
//                    startActivity(intent2)
                    supportFragmentManager.beginTransaction().replace(R.id.driverLayout, fragment)
                        .commit()
                }
            }
            R.id.bookmarks -> {
                if (!isLogin) {
                    Toast.makeText(
                        this@FeedActivity,
                        "Не выполнен вход в аккаунт",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    fragment = BookmarksFragment()
//                    val intent2 = Intent(this@FeedActivity,BookmarksActivity::class.java)
//                    startActivity(intent2)
                    supportFragmentManager.beginTransaction().replace(R.id.driverLayout, fragment)
                        .commit()
                }
            }
        }
        driverLayout.closeDrawer(GravityCompat.START)
//        onStop()
        return true
    }

    private fun callNetworkConnection() {
        checkNetworkConnection = CheckInternetConnection(application)
        checkNetworkConnection.observe(this) { isConnected ->
            if (isConnected) {
                if (disconnected) {
                    Toast.makeText(this@FeedActivity, "Подлючение установлено", Toast.LENGTH_LONG)
                        .show()
                    disconnected = false
                }

            } else {
                disconnected = true
                Toast.makeText(this@FeedActivity, "Нет подключения к интернету", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onStop() {
        if (userModel.login != "") {
            USERLOGIN = userModel.login
            prefs.edit().putString(APP_PREFERENCES, USERLOGIN).apply()
        }
        super.onStop()
    }

    override fun onStart() {
        val lu = prefs.getString(APP_PREFERENCES, "").toString()
        if (lu != "") {
            userModel = getUserByLogin(lu)
            isLogin = true
            navUpdate()
            newsAdapter.setUser(userModel)
        }
        getCategories()
        super.onStart()
    }
}