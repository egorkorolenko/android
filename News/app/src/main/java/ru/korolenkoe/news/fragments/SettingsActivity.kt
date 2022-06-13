package ru.korolenkoe.news.fragments

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ru.korolenkoe.news.R
import ru.korolenkoe.news.adapter.CategoryAdapter
import ru.korolenkoe.news.adapter.CategoryAdapterSetting
import ru.korolenkoe.news.db.UserDatabase
import ru.korolenkoe.news.model.CategoryModel
import ru.korolenkoe.news.model.UserModel
import ru.korolenkoe.news.repository.UserRepository
import ru.korolenkoe.news.utils.ClickCategoryInterface

class SettingsActivity : AppCompatActivity() {

    private var categories: ArrayList<CategoryModel> = arrayListOf()
    private lateinit var okButton: Button
    private lateinit var userModel: UserModel

    private lateinit var database: UserDatabase
    private lateinit var repository: UserRepository

    private lateinit var categoryAdapter: CategoryAdapterSetting
    private lateinit var recyclerViewCategory: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_fragment)

        okButton = findViewById(R.id.settingOkButton)
        database = UserDatabase.getDatabase(this.application)
        repository = UserRepository(database.userDao())
        recyclerViewCategory = findViewById(R.id.recyclerViewCategorySettings)
        val argument: Bundle? = intent.extras

        val login = argument?.get("login").toString()
        if (login != "null") {
            userModel = getUserByLogin(login)
            for(category in userModel.categories){
                categories.add(CategoryModel(category))
            }
        }

        categoryAdapter = CategoryAdapterSetting(categories, this, object : ClickCategoryInterface {
            override fun onClickCategory(position: Int) {
                val category: String = categories[position].category
            }
        })
        recyclerViewCategory.adapter = categoryAdapter
        categoryAdapter.notifyItemChanged(0, categories.size)
    }

    private fun getUserByLogin(login: String): UserModel {
        return repository.getUserByLogin(login)
    }

}