package ru.korolenkoe.news.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.util.stream.Collectors

class CategoryConverter {

    @RequiresApi(Build.VERSION_CODES.N)
    @TypeConverter
    fun fromCategories(category: List<String>): String? {
        return category.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toCategories(data: String): List<String> {
        return data.split(',')
    }}