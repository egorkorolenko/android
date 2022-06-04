package ru.korolenkoe.news.utils

import androidx.room.TypeConverter
import ru.korolenkoe.news.model.Articles

class ArticlesConverter {

    @TypeConverter
    fun fromArticles(articles:List<Articles>):String{
        var articlesString = ""
        for (article in articles){
            articlesString += article.title +','+ article.description+','+article.urlToImage +','+ article.url +','+ article.content +','+ article.publishedAt
            articlesString += ";"
        }
        return articlesString
    }

    @TypeConverter
    fun toArticles(articles:String):List<Articles>{
        val articlesList = mutableListOf<Articles>()
        val listStr: List<String> = articles.split(';')
        for(l in listStr){
            val strings  = l.split(',')
            if(l!=""){
                val a = Articles(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5])
                articlesList.add(a)}
        }
        return articlesList
    }
}