package com.maku.pombe.common.data.cache.daos

import androidx.room.*
import com.maku.pombe.common.data.cache.model.cachedLatest.CachedLatest
import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import io.reactivex.Flowable

@Dao
interface PopularDao {
    @Transaction
    @Query("SELECT * FROM popular ORDER BY idDrink DESC")
    fun getAllPopularDrinks(): Flowable<List<CachedPopular>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularDrinks(
        cachedPopular: List<CachedPopular>
    )
}