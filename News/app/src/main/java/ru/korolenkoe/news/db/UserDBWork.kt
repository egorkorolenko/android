package ru.korolenkoe.news.db

import androidx.appcompat.app.AppCompatActivity
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.UserModel
import ru.korolenkoe.news.repository.UserRepository

class UserDBWork : AppCompatActivity() {


    fun addCategory(userModel: UserModel, category: String, database: UserDatabase) {
        val repository = UserRepository(database.userDao())
        val newList: MutableList<String> = mutableListOf()
        for (categoryFromUser in userModel.categories) {
            newList.add(categoryFromUser)
        }
        newList.add(category)
        userModel.categories = newList.distinct()
        repository.updateUser(userModel)
    }

}