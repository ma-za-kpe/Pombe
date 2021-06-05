package com.maku.pombe.data.local.Typeconvertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maku.pombe.data.models.recent.Recent

class RecentRecipeConvertor {
    var gson = Gson()

    @TypeConverter
    fun recentCocktailToString(recent: Recent): String{
        return gson.toJson(recent)
    }
    @TypeConverter
    fun stringToRecentCocktail(data: String): Recent{
        val listType = object: TypeToken<Recent>(){}.type
        return gson.fromJson(data, listType)
    }

}