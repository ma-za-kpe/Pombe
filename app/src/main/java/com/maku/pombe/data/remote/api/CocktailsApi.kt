package com.maku.pombe.data.remote.api

import com.maku.pombe.models.recent.Drink
import com.maku.pombe.models.recent.Recent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsApi {

    // get recent cocktails
    @GET("/api/json/v2/9973533/recent.php")
    suspend fun getRecentCocktail(
    ): Response<Recent>
}