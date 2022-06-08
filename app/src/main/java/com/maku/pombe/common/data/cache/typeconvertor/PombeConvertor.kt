package com.maku.pombe.common.data.cache.typeconvertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maku.pombe.common.data.api.model.latest.DrinkLatest
import com.maku.pombe.common.data.api.model.popular.DrinkPopular
import com.maku.pombe.common.domain.model.shared.Ingredients
import com.maku.pombe.common.domain.model.shared.Instructions
import com.maku.pombe.common.domain.model.shared.Measures

class PombeConvertor {
    var gson = Gson()

    @TypeConverter
    fun measureToString(measures: List<Measures>): String{
        return gson.toJson(measures)
    }
    @TypeConverter
    fun stringToMeasure(data: String): List<Measures>{
        val listType = object: TypeToken<List<Measures>>(){}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun instructionToString(instructions: List<Instructions>): String{
        return gson.toJson(instructions)
    }
    @TypeConverter
    fun stringToInstruction(data: String): List<Instructions>{
        val listType = object: TypeToken<List<Instructions>>(){}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun ingreidentToString(ingredients: List<Ingredients>): String{
        return gson.toJson(ingredients)
    }
    @TypeConverter
    fun stringToIngreident(data: String): List<Ingredients>{
        val listType = object: TypeToken<List<Ingredients>>(){}.type
        return gson.fromJson(data, listType)
    }

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
