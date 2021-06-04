package com.maku.pombe.data.remote.datasource

import com.maku.pombe.data.remote.api.CocktailsApi
import com.maku.pombe.models.recent.Drink
import com.maku.pombe.models.recent.Recent
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val cocktailsApi: CocktailsApi) {
    suspend fun getRecentCocktails(): Response<Recent> {
        return cocktailsApi.getRecentCocktail()
    }
}