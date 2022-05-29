package ru.korolenkoe.news.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ru.korolenkoe.news.R

class RegistrationActivity : AppCompatActivity() {

    lateinit var userName: EditText
    lateinit var login: EditText
    lateinit var password: EditText
    lateinit var passwordAgain: EditText
    lateinit var registrationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        userName = findViewById(R.id.userNameEV)
        login = findViewById(R.id.loginEV)
        password = findViewById(R.id.passwordEV)
        passwordAgain = findViewById(R.id.passwordAgainEV)
        registrationButton = findViewById(R.id.registrationOk)


    }
}