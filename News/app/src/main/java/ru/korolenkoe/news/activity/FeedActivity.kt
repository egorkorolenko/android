package ru.korolenkoe.news.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.provider.MediaStore.Images.Media.getContentUri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
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
import ru.korolenkoe.news.fragments.BookmarksActivity
import ru.korolenkoe.news.fragments.DownloadActivity
import ru.korolenkoe.news.fragments.ProfileActivity
import ru.korolenkoe.news.fragments.SettingsActivity
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.CategoryModel
import ru.korolenkoe.news.model.NewsModel
import ru.korolenkoe.news.model.UserModel
import ru.korolenkoe.news.repository.UserRepository
import ru.korolenkoe.news.utils.CheckInternetConnection
import ru.korolenkoe.news.utils.ClickCategoryInterface
import ru.korolenkoe.news.utils.RetrofitAPI
import java.io.File


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
    private lateinit var userNameNavigationViewImage: CircleImageView
    private lateinit var database: UserDatabase
    private lateinit var repository: UserRepository
    private var userModel: UserModel = UserModel(-1, "", "??????????", "", "", listOf(), listOf())
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
        userNameNavigationViewImage = headerContainer.findViewById(R.id.profileImage)

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

        getNews("??????")

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
            if (signInButton.text.toString() == "??????????") {
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
            signInButton.text = "??????????"
            userNameNavigationView.text = userModel.name
            if(userModel.image!=""){
//                val file = File(userModel.image)
            val uri: Uri = Uri.parse(userModel.image)
//                val bitmap: Bitmap = BitmapFactory.decodeFile(userModel.image)
//                Picasso.get().load(file).fit().into(userNameNavigationViewImage)
                userNameNavigationViewImage.setImageURI(userModel.image.toUri())
            }
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
        alertDialogBuilder.setPositiveButton("????") { _, _ ->
            signInButton.text = "??????????"
            USERLOGIN = ""
            userNameNavigationView.text = "??????????"
            userModel = UserModel(-1, "", "??????????", "", "", listOf(), listOf())
            isLogin = false
            getCategories()
            prefs.edit().remove(APP_PREFERENCES).apply()
        }
        alertDialogBuilder.setNegativeButton("????????????") { _, _ ->
        }
        val alertDialogCreated: AlertDialog = alertDialogBuilder.create();
        alertDialogCreated.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getCategories() {
        if (!isLogin) {
            categories.add(CategoryModel("??????"))
            categories.add(CategoryModel("??????????????"))
            categories.add(CategoryModel("????????????"))
            categories.add(CategoryModel("??????????????????????"))
            categories.add(CategoryModel("????????????????"))
            categories.add(CategoryModel("??????????"))
            categories.add(CategoryModel("??????????"))
            categories.add(CategoryModel("????????????????????"))
            categories.add(CategoryModel("+ ????????"))
        } else {
            categories.clear()
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
        alertDialogBuilder.setPositiveButton("????????????") { _, _ ->
            if (input.text.toString() != "" && !checkUniqueCategory(input.text.toString())) {
                categories.removeAt(categories.size - 1)
                categories.add(CategoryModel(input.text.toString()))
                categories.add(CategoryModel("+ ????????"))
                categoryAdapter.notifyDataSetChanged()
                if (userModel.login != "") {
                    val userDBWork = UserDBWork()
                    userDBWork.addCategory(userModel, input.text.toString(), database)
                }
            }
        }
        alertDialogBuilder.setNegativeButton("????????????") { _, _ ->
        }
        val alertDialogCreated: AlertDialog = alertDialogBuilder.create();
        alertDialogCreated.show()
        articlesArrayList.clear()
    }

    private fun checkUniqueCategory(category: String): Boolean {
        var isRepeat = false
        for (categoryNew in categories) {
            if (category == categoryNew.category) {
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

        if (category == "+ ????????") {
            addCategory()
        }

        val categoryEn = when (category) {
            "??????????????" -> "general"
            "??????" -> "All"
            "????????????" -> "business"
            "??????????????????????" -> "entertainment"
            "????????????????" -> "health"
            "??????????" -> "science"
            "??????????" -> "sports"
            "????????????????????" -> "technology"
            else -> {
                null
            }
        }

        articlesArrayList.clear()

        if (categoryEn == null) {
            categoryUrl = if (category != "+ ????????") {
                "https://newsapi.org/v2/everything?q=$category&from=2022-06-14&sortBy=publishedAt&apiKey=ed7b9a5f85274d88ac578e199f7cf65e"
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
        when (item.itemId) {
            R.id.profileMenu -> {
                if (!isLogin) {
                    Toast.makeText(
                        this@FeedActivity,
                        "???? ???????????????? ???????? ?? ??????????????",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val intent2 = Intent(this@FeedActivity, ProfileActivity::class.java)
                    intent2.putExtra("login", userModel.login)
                    startActivity(intent2)
                }
            }
            R.id.download -> {
                if (!isLogin) {
                    Toast.makeText(
                        this@FeedActivity,
                        "???? ???????????????? ???????? ?? ??????????????",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val intent2 = Intent(this@FeedActivity, DownloadActivity::class.java)
                    intent2.putExtra("login", userModel.login)
                    startActivity(intent2)
                }
            }
            R.id.settings -> {
                if (!isLogin) {
                    Toast.makeText(
                        this@FeedActivity,
                        "???? ???????????????? ???????? ?? ??????????????",
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                val intent2 = Intent(this@FeedActivity, SettingsActivity::class.java)
                intent2.putExtra("login", userModel.login)
                startActivity(intent2)
                }
            }
            R.id.bookmarks -> {
                if (!isLogin) {
                    Toast.makeText(
                        this@FeedActivity,
                        "???? ???????????????? ???????? ?? ??????????????",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val intent2 = Intent(this@FeedActivity, BookmarksActivity::class.java)
                    intent2.putExtra("login", userModel.login)
                    startActivity(intent2)
                }
            }
            R.id.feed -> {
                val intent2 = Intent(this, FeedActivity::class.java)
                startActivity(intent2)
            }
        }
        driverLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun callNetworkConnection() {
        checkNetworkConnection = CheckInternetConnection(application)
        checkNetworkConnection.observe(this) { isConnected ->
            if (isConnected) {
                if (disconnected) {
                    Toast.makeText(this@FeedActivity, "???????????????????? ??????????????????????", Toast.LENGTH_LONG)
                        .show()
                    disconnected = false
                }

            } else {
                disconnected = true
                Toast.makeText(this@FeedActivity, "?????? ?????????????????????? ?? ??????????????????", Toast.LENGTH_LONG)
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