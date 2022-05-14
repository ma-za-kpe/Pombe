package com.maku.pombe.common.data.cache

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import com.maku.pombe.common.data.cache.model.cachedCategory.CategoryDbModel
import com.maku.pombe.common.data.cache.model.cachedLatest.CachedLatest
import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import com.maku.pombe.common.domain.model.category.CategoryModel
import io.reactivex.Flowable

interface Cache {
    fun getPopularDrinks(): Flowable<List<CachedPopular>>
    suspend fun storePopularDrinks(drinks: List<CachedPopular>)

    fun getLatestDrinks(): Flowable<List<CachedLatest>>
    suspend fun storeLatestDrinks(drinks: List<CachedLatest>)

    fun getAllCategory(): Flowable<List<CategoryDbModel>>
    fun getAllCategorySync(): List<CategoryDbModel>
    fun findCategoryById(id: Long): LiveData<CategoryDbModel>
    fun findCategoryByIdSync(id: Long): CategoryDbModel

    // search
    fun searchCocktailsBy(name: String): Flowable<List<CachedLatest>>
}