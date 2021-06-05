package com.maku.pombe.utils

import com.maku.pombe.BuildConfig

class Constants {
    //recent https://www.thecocktaildb.com/api/json/v2/xyz/recent.php
    companion object{
        const val BASE_URL = "https://www.thecocktaildb.com"
        var apiKey: String = BuildConfig.API_KEY

        // api query keys
        const val query_api_key = "apiKey"

        // room
        const val DATABASE = "cocktails_db"
        const val RECENT_COCKTAIL_TABLE = "recent_cocktail_table"

        // preferences
        const val PREFERENCES_NAME = "cocktail_preferences"

        //INTERNET
        const val PREFERENCES_BACK_ONLINE = "back online"
    }

}