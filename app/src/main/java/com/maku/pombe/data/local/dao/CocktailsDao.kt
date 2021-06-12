package com.maku.pombe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maku.pombe.data.local.entities.LatestCocktailsEntity
import com.maku.pombe.data.local.entities.PopularCocktailsEntity
import com.maku.pombe.data.local.entities.RecentCocktailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailsDao {
    // recent
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentCocktails(recentCocktailsEntity: RecentCocktailsEntity)

    @Query("SELECT * FROM RECENT_COCKTAIL_TABLE ORDER BY id ASC")
    fun readRecentCocktails(): Flow<List<RecentCocktailsEntity>>

    // popular
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularCocktails(popularCocktailsEntity: PopularCocktailsEntity)

    @Query("SELECT * FROM POPULAR_COCKTAIL_TABLE ORDER BY id ASC")
    fun readPopularCocktails(): Flow<List<PopularCocktailsEntity>>

    // latest
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLatestCocktails(latestCocktailsEntity: LatestCocktailsEntity)

    @Query("SELECT * FROM LATEST_COCKTAIL_TABLE ORDER BY id ASC")
    fun readLatestCocktails(): Flow<List<LatestCocktailsEntity>>

}