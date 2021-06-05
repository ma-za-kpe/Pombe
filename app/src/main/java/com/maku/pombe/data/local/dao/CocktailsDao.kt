package com.maku.pombe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maku.pombe.data.local.entities.RecentCocktailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailsDao {
    // recent
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentCocktails(recentCocktailsEntity: RecentCocktailsEntity)

    @Query("SELECT * FROM RECENT_COCKTAIL_TABLE ORDER BY id ASC")
    fun readRecentCocktails(): Flow<List<RecentCocktailsEntity>>
}