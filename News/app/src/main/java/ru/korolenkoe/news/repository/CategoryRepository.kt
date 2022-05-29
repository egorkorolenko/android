package ru.korolenkoe.news.repository

import ru.korolenkoe.news.dao.CategoryDao
import ru.korolenkoe.news.model.CategoryModel

class CategoryRepository(private val categoryDao: CategoryDao) {

    val readAllData: List<CategoryModel> = categoryDao.getAllCategory()

    fun addCategory(categoryModel: CategoryModel){
        categoryDao.insertCategory(categoryModel)
    }

}