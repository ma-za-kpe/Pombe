package com.maku.pombe.common.data.cache.daos

import androidx.room.*
import com.maku.pombe.common.data.cache.model.cachedLatest.CachedLatest
import io.reactivex.Flowable

@Dao
interface LatestDao {

    @Transaction
    @Query("SELECT * FROM latest ORDER BY idDrink DESC")
    fun getAllLatestDrinks(): Flowable<List<CachedLatest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLatestDrinks(
        cachedLatest: List<CachedLatest>
    )

    @Transaction
    @Query("SELECT * FROM latest WHERE strDrink LIKE '%' || :name || '%' ")
    fun searchCocktailsBy(name: String): Flowable<List<CachedLatest>>


}