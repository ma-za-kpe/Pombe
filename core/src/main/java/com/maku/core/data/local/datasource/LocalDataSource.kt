package com.maku.core.data.local.datasource

import com.maku.core.data.local.dao.CocktailsDao
import com.maku.core.data.local.entities.LatestCocktailsEntity
import com.maku.core.data.local.entities.PopularCocktailsEntity
import com.maku.core.data.local.entities.RecentCocktailsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource  @Inject constructor(private val dao: CocktailsDao) {

    fun readRecentCocktails(): Flow<List<RecentCocktailsEntity>> {
        return dao.readRecentCocktails()
    }

    suspend fun insertRecentCocktails(recentCocktailsEntity: RecentCocktailsEntity) {
        dao.insertRecentCocktails(recentCocktailsEntity)
    }

    fun readPopularCocktails(): Flow<List<PopularCocktailsEntity>> {
        return dao.readPopularCocktails()
    }

    suspend fun insertPopularCocktails(popularCocktailsEntity: PopularCocktailsEntity) {
        dao.insertPopularCocktails(popularCocktailsEntity)
    }

    fun readLatestCocktails(): Flow<List<LatestCocktailsEntity>> {
        return dao.readLatestCocktails()
    }

    suspend fun insertLatestCocktails(latestCocktailsEntity: LatestCocktailsEntity) {
        dao.insertLatestCocktails(latestCocktailsEntity)
    }

}