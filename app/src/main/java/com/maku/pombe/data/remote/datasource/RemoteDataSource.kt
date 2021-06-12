package com.maku.pombe.data.remote.datasource

import com.maku.pombe.data.models.latest.Latest
import com.maku.pombe.data.models.popular.Popular
import com.maku.pombe.data.remote.api.CocktailsApi
import com.maku.pombe.data.models.recent.Recent
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val cocktailsApi: CocktailsApi) {
    suspend fun getRecentCocktails(): Response<Recent> {
        return cocktailsApi.getRecentCocktail()
    }

    suspend fun getPopularCocktails(): Response<Popular> {
        return cocktailsApi.getPopularCocktail()
    }

    suspend fun getLatestCocktails(): Response<Latest> {
        return cocktailsApi.getLatestsCocktail()
    }
}