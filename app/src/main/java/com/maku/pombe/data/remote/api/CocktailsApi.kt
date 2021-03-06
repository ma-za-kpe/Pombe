package com.maku.pombe.data.remote.api

import com.maku.pombe.BuildConfig
import com.maku.pombe.data.models.latest.Latest
import com.maku.pombe.data.models.popular.Popular
import com.maku.pombe.data.models.recent.Recent
import retrofit2.Response
import retrofit2.http.GET

interface CocktailsApi {

    // get recent cocktails
    @GET("/api/json/v2/${BuildConfig.API_KEY}/recent.php")
    suspend fun getRecentCocktail(
    ): Response<Recent>

    // get popular cocktails 9973533
    @GET("/api/json/v2/${BuildConfig.API_KEY}/popular.php")
    suspend fun getPopularCocktail(
    ): Response<Popular>

    // get latest cocktails 9973533
    @GET("/api/json/v2/${BuildConfig.API_KEY}/latest.php")
    suspend fun getLatestsCocktail(
    ): Response<Latest>
}