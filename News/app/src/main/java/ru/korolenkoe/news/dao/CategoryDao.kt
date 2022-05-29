package ru.korolenkoe.news.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.korolenkoe.news.model.CategoryModel

@Dao
interface CategoryDao {

    @Insert
    fun insertCategory(category : CategoryModel)

    @Delete
    fun deleteCategory(category: CategoryModel)

    @Query("SELECT * from category_db")
    fun getAllCategory():List<CategoryModel>
}