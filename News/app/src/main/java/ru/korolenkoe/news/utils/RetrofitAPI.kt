package ru.korolenkoe.news.utils

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import ru.korolenkoe.news.model.NewsModel

interface RetrofitAPI {
    @GET
    fun getAllNews(@Url url: String): Call<NewsModel>

    @GET
    fun getNewsByCategory(@Url url: String): Call<NewsModel>

    @GET
    fun getNewsByQuestion(@Url q: String): Call<NewsModel>
}