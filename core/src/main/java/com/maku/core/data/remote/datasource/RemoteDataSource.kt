package com.maku.core.data.remote.datasource

import com.maku.core.domain.latest.Latest
import com.maku.core.domain.popular.Popular
import com.maku.core.data.remote.api.CocktailsApi
import com.maku.core.domain.recent.Recent
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