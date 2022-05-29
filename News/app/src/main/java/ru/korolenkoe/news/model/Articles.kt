package ru.korolenkoe.news.model

import androidx.room.Entity

@Entity
class Articles(
    var title: String?,
    var description: String?,
    var urlToImage: String?,
    var url: String?,
    var content: String?,
    var publishedAt: String?
)

