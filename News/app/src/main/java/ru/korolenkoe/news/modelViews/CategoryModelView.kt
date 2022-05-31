package ru.korolenkoe.news.modelViews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.korolenkoe.news.db.CategoryDatabase
import ru.korolenkoe.news.model.CategoryModel
import ru.korolenkoe.news.repository.CategoryRepository

class CategoryModelView(application: Application): AndroidViewModel(application) {

    //Возможно придется заменить на LiveData<<List<>>>
    private val readAllData : List<CategoryModel>
    private val repository: CategoryRepository

    init {
        val categoryDao = CategoryDatabase.getDatabase(application).categoryDao()
        repository = CategoryRepository(categoryDao)
        readAllData = repository.readAllData
    }

    fun addCategory(categoryModel: CategoryModel){
        repository.addCategory(categoryModel)
    }

    fun getAllCategories(): List<CategoryModel>{
        return repository.readAllData
    }
}