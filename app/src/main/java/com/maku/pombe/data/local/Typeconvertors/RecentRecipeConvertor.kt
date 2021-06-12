package com.maku.pombe.data.local.Typeconvertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maku.pombe.data.models.latest.Latest
import com.maku.pombe.data.models.popular.Popular
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

    @TypeConverter
    fun popularCocktailToString(popular: Popular): String{
        return gson.toJson(popular)
    }
    @TypeConverter
    fun stringToPopularCocktail(data: String): Popular{
        val listType = object: TypeToken<Popular>(){}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun latestCocktailToString(latest: Latest): String{
        return gson.toJson(latest)
    }
    @TypeConverter
    fun stringToLatestCocktail(data: String): Latest{
        val listType = object: TypeToken<Latest>(){}.type
        return gson.fromJson(data, listType)
    }

}