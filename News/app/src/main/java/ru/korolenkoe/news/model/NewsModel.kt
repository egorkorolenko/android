package ru.korolenkoe.news.model

class NewsModel(
    var totalResults: Int,
    var status: String,
    var articles: ArrayList<Articles>
)