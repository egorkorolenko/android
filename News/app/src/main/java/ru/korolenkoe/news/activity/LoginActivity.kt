package ru.korolenkoe.news.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ru.korolenkoe.news.R
import ru.korolenkoe.news.db.UserDatabase
import ru.korolenkoe.news.repository.UserRepository

class LoginActivity : AppCompatActivity() {

    private lateinit var registrationButton: Button
    private lateinit var loginButton: Button
    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var database: UserDatabase
    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        registrationButton = findViewById(R.id.registration)
        login = findViewById(R.id.login)
        password = findViewById(R.id.password_sing_in)
        loginButton = findViewById(R.id.sing_in_button)

        login.setTextColor(Color.WHITE)
        password.setTextColor(Color.WHITE)

        val loginText : Editable?= login.text
        val passwordText:Editable? = password.text


        registrationButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            if(checkData(loginText.toString(),passwordText.toString())){
            val intent = Intent(this@LoginActivity, FeedActivity::class.java)
            startActivity(intent)}
        }
    }
    
    private fun checkData(loginStr:String, passwordStr:String):Boolean{
        if(loginStr==""){
            login.hint = "Введите логин"
            login.setHintTextColor(Color.RED)
            return false
        }
        if(passwordStr==""){
            password.hint = "Введите пароль"
            password.setHintTextColor(Color.RED)
            return false
        }
        return true
    }
}