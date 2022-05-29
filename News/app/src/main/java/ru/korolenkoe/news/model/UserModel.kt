package ru.korolenkoe.news.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String,
    val secondName: String,
    val email: String,
    val image: String,
    val bookMarks: List<Articles>,
    val categoryes: List<String>
)