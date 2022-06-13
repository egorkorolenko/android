package ru.korolenkoe.news.menuAction

import ru.korolenkoe.news.db.UserDatabase
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.UserModel
import ru.korolenkoe.news.repository.UserRepository

class AddIntoBookmarks {

    fun addBookMark(userModel: UserModel, database: UserDatabase, article: Articles) {
        val repository = UserRepository(database.userDao())
        val newsBookMarksList: MutableList<Articles> = userModel.bookMarks.toMutableList()
        newsBookMarksList.add(article)
        userModel.bookMarks = newsBookMarksList.distinct()
        repository.updateUser(userModel)
    }
}