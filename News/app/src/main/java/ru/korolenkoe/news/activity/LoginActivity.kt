package ru.korolenkoe.news.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ru.korolenkoe.news.R

class LoginActivity : AppCompatActivity() {

    lateinit var registrationButton: Button
    lateinit var login: EditText
    lateinit var password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        registrationButton = findViewById(R.id.registration)
        login = findViewById(R.id.login)
        password = findViewById(R.id.password_sing_in)

        registrationButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}