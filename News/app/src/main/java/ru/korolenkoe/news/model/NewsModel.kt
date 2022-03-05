package ru.korolenkoe.news.model

import ru.korolenkoe.news.model.Articles

class NewsModel(
    var totalResults: Int,
    var status: String,
    var articles: ArrayList<Articles>
)