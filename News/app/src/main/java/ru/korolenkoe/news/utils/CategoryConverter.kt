package ru.korolenkoe.news.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import ru.korolenkoe.news.model.CategoryModel
import java.util.*
import java.util.stream.Collectors

class CategoryConverter {

    @RequiresApi(Build.VERSION_CODES.N)
    @TypeConverter
    fun fromCategory(category: List<String>): String? {
        return category.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toCategory(data: String): List<List<String>> {
        return listOf(data.split(','))
    }}