package ru.korolenkoe.news.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.korolenkoe.news.model.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
}