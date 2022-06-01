package ru.korolenkoe.news.model

import androidx.room.*
import ru.korolenkoe.news.utils.CategoryConverter

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,
    @ColumnInfo(name = "login")val name: String,
    @ColumnInfo(name = "name") val login: String,
    @ColumnInfo(name = "image")val image: String,
    @ColumnInfo(name = "password")val password: String,
//    @ColumnInfo(name = "bookMarks")val bookMarks: List<Articles>?,
//    @TypeConverters(CategoryConverter::class)
//    val categories: List<String>?
)