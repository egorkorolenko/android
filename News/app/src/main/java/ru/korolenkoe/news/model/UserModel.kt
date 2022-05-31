package ru.korolenkoe.news.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,
    @ColumnInfo(name = "login")val name: String?,
    @ColumnInfo(name = "name") val login: String,
    @ColumnInfo(name = "image")val image: String?,
    @ColumnInfo(name = "bookMarks")val bookMarks: List<Articles>,
    @ColumnInfo(name = "categories")val categories: List<String>
)