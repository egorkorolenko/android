package ru.korolenkoe.news.model

class UserModel(
    val name: String,
    val secondName: String,
    email: String,
    image: String,
    bookMarks: List<Articles>,
    categoryes: List<String>
)