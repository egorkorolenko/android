package ru.korolenkoe.news.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.korolenkoe.news.dao.UserDao
import ru.korolenkoe.news.model.Articles
import ru.korolenkoe.news.model.UserModel

@Database(entities = [UserModel::class], version = 2)
abstract class UserDatabase : RoomDatabase(){

    public abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context) : UserDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "users"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}