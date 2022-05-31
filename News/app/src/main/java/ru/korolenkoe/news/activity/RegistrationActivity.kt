package ru.korolenkoe.news.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ru.korolenkoe.news.R
import ru.korolenkoe.news.db.UserDatabase
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.UserModel
import ru.korolenkoe.news.modelViews.UserModelView
import ru.korolenkoe.news.repository.UserRepository

class RegistrationActivity : AppCompatActivity() {

    private lateinit var userNameET: EditText
    private lateinit var loginET: EditText
    private lateinit var passwordET: EditText
    private lateinit var passwordAgainET: EditText
    private lateinit var registrationButtonET: Button
    private lateinit var modelView: UserModelView
    private val database by lazy { UserDatabase.getDatabase(application) }
    val repository by lazy { UserRepository(database.userDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        userNameET = findViewById(R.id.userNameEV)
        loginET = findViewById(R.id.loginEV)
        passwordET = findViewById(R.id.passwordEV)
        passwordAgainET = findViewById(R.id.passwordAgainEV)
        registrationButtonET = findViewById(R.id.registrationOk)

        val userName = userNameET.text.toString()
        val login = loginET.text.toString()
        val passwordET = passwordET.text.toString()
        val passwordAgain = passwordAgainET.text.toString()

        val articles: List<Articles> = arrayListOf()
        val categories :List<String> = arrayListOf()
        val userModel = UserModel(0,userName,login,"",articles,categories)


        registrationButtonET.setOnClickListener {
            checkData(userModel)
            val intent = Intent(this@RegistrationActivity, FeedActivity::class.java)
            startActivity(intent)
        }
    }

    private fun insertUser(userModel: UserModel){
        modelView.insertUser(userModel)
    }

    private fun checkData(userModel: UserModel){
        insertUser(userModel)
    }





}