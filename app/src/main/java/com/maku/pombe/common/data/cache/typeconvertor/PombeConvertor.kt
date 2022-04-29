package com.maku.pombe.common.data.cache.typeconvertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maku.pombe.common.data.api.model.latest.DrinkLatest
import com.maku.pombe.common.data.api.model.popular.DrinkPopular

class PombeConvertor {
    var gson = Gson()

    @TypeConverter
    fun latestToString(drinkLatest: List<DrinkLatest>): String{
        return gson.toJson(drinkLatest)
    }
    @TypeConverter
    fun stringToLatest(data: String): List<DrinkLatest>{
        val listType = object: TypeToken<List<DrinkLatest>>(){}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun popularToString(drinkPopular: List<DrinkPopular>): String{
        return gson.toJson(drinkPopular)
    }
    @TypeConverter
    fun stringToPopular(data: String): List<DrinkPopular>{
        val listType = object: TypeToken<List<DrinkPopular>>(){}.type
        return gson.fromJson(data, listType)
    }
}
