package ru.korolenkoe.news.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.korolenkoe.news.dao.CategoryDao
import ru.korolenkoe.news.model.CategoryModel

@Database(entities = [CategoryModel::class], version = 1, exportSchema = false)
 abstract class CategoryDatabase : RoomDatabase(){

     abstract fun categoryDao(): CategoryDao

     companion object{
         @Volatile
         private var INSTANCE: CategoryDatabase? = null

         fun getDatabase(context: Context) : CategoryDatabase{
             val tempInstance = INSTANCE
             if(tempInstance!=null){
                 return tempInstance
             }
             synchronized(this){
                 val instance = Room.databaseBuilder(
                     context.applicationContext,
                     CategoryDatabase::class.java,
                     "category_db"
                 ).build()
                 INSTANCE = instance
                 return instance
             }
         }
     }
}