package ru.korolenkoe.news.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.korolenkoe.news.R
import ru.korolenkoe.news.db.UserDatabase
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.CategoryModel
import ru.korolenkoe.news.model.UserModel
import ru.korolenkoe.news.repository.UserRepository

class RegistrationActivity : AppCompatActivity() {

    private lateinit var userNameET: EditText
    private lateinit var loginET: EditText
    private lateinit var passwordET: EditText
    private lateinit var passwordAgainET: EditText
    private lateinit var registrationButtonET: Button
    //    private lateinit var modelView: UserModelView
    private lateinit var database: UserDatabase
    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        userNameET = findViewById(R.id.userNameEV)
        loginET = findViewById(R.id.loginEV)
        passwordET = findViewById(R.id.passwordEV)
        passwordAgainET = findViewById(R.id.passwordAgainEV)
        registrationButtonET = findViewById(R.id.registrationOk)
        database = UserDatabase.getDatabase(this.application)
        repository = UserRepository(database.userDao())

        userNameET.setTextColor(Color.WHITE)
        loginET.setTextColor(Color.WHITE)
        passwordET.setTextColor(Color.WHITE)
        passwordAgainET.setTextColor(Color.WHITE)

        val userName:Editable? = userNameET.text
        val login:Editable? = loginET.text
        val passwordET:Editable? = passwordET.text
        val passwordAgain:Editable? = passwordAgainET.text

        val articles: List<Articles> = arrayListOf()
        val categories :List<String> = arrayListOf("Всё","Главное","Бизнес","Развлечение","Здоровье","Наука","Спорт","Технологии","+ своя")

        registrationButtonET.setOnClickListener {
            val user = createUser(0, userName.toString(), login.toString(),passwordET.toString(),categories,articles)
            if(checkData(user,passwordAgain.toString())){
                val intentSendLogin = Intent(this@RegistrationActivity,FeedActivity::class.java)
                intentSendLogin.putExtra(user.login,"login")
                startActivity(intentSendLogin)
            }
        }
    }

    private fun checkLoginUnique(login:String):Boolean{
       val userModel = repository.getUserByLogin(login)
        if(userModel!=null){
            return false
        }
        return true
    }

    private fun createUser(id:Int, name: String, login: String, password: String,categories:List<String>,articles: List<Articles>): UserModel {
        return UserModel(id, name, login, "", password,articles,categories)
    }

    private fun insertUser(userModel: UserModel){
        repository.insertUser(userModel)
    }

    private fun checkData(userModel: UserModel,p2: String):Boolean{
        if(userModel.login==""){
            loginET.hint = "Введите логин"
            loginET.setHintTextColor(Color.RED)
            return false
        }
        if(!checkLoginUnique(userModel.login)){
            Toast.makeText(this@RegistrationActivity,"Логин уже занят",Toast.LENGTH_LONG).show()
            loginET.hint = "Логин уже занят"
            loginET.setHintTextColor(Color.RED)
            return false
        }
        if(userModel.name==""){
            userNameET.hint = "Введите имя"
            userNameET.setHintTextColor(Color.RED)
            return false
        }
        if(userModel.password==""){
            passwordET.hint = "Введите пароль"
            passwordET.setHintTextColor(Color.RED)
            return false
        }
        if(p2==""){
            passwordAgainET.hint = "Повторите пароль"
            passwordAgainET.setHintTextColor(Color.RED)
            return false
        }
        if (checkPasswords(userModel.password,p2))
        insertUser(userModel)
        else return false
        return true
    }

    private fun checkPasswords(p1:String,p2:String):Boolean{
        if(p1.length<6||p2.length<6){
            passwordET.setText("Длина должна быть больше 8 знаков")
            passwordET.setHintTextColor(Color.RED)
            Toast.makeText(this@RegistrationActivity,"длина<6",Toast.LENGTH_LONG).show()
            return false
        }
        if(p1!=p2){
            Toast.makeText(this@RegistrationActivity,"Пароли не совпадают",Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

}