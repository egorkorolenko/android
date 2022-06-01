package ru.korolenkoe.news.repository

import ru.korolenkoe.news.dao.UserDao
import ru.korolenkoe.news.model.UserModel

class UserRepository(private val userDao: UserDao) {

    val readAllData: List<UserModel> = userDao.readAllData()

    fun insertUser(userModel: UserModel){
        userDao.insertUser(userModel)
    }

//    fun getUserByLogin(login:String):UserModel{
//        return userDao.getUserByLogin(login)
//    }
}