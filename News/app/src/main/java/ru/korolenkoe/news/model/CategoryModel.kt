package ru.korolenkoe.news.model

import androidx.room.Entity

@Entity(tableName = "category_db")
class CategoryModel(
    var category: String
)