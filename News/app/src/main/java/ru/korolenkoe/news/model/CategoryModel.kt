package ru.korolenkoe.news.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_db")
class CategoryModel(
    @PrimaryKey
    var category: String
)