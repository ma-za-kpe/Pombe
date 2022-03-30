package com.maku.pombe.common.data.cache.typeconvertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maku.pombe.common.data.api.model.Drink

class PombeConvertor {
    var gson = Gson()

    @TypeConverter
    fun popularToString(weatherResponse: List<Drink>): String{
        return gson.toJson(weatherResponse)
    }
    @TypeConverter
    fun stringToPopular(data: String): List<Drink>{
        val listType = object: TypeToken<List<Drink>>(){}.type
        return gson.fromJson(data, listType)
    }
}
