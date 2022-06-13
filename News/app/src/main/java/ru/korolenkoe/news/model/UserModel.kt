package ru.korolenkoe.news.model

import androidx.room.*
import ru.korolenkoe.news.utils.ArticlesConverter
import ru.korolenkoe.news.utils.CategoryConverter

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "password") val password: String,
    @TypeConverters(ArticlesConverter::class)
    @ColumnInfo(name = "bookMarks") var bookMarks: List<Articles>,
    @TypeConverters(CategoryConverter::class)
    var categories: List<String>
)