package com.maku.pombe.common.data.cache

import androidx.lifecycle.LiveData
import com.maku.pombe.common.data.cache.daos.CategoryDao
import com.maku.pombe.common.data.cache.daos.LatestDao
import com.maku.pombe.common.data.cache.daos.PopularDao
import com.maku.pombe.common.data.cache.model.cachedCategory.CategoryDbModel
import com.maku.pombe.common.data.cache.model.cachedLatest.CachedLatest
import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import io.reactivex.Flowable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class RoomCache @Inject constructor(
    private val popularDao: PopularDao,
    private val latestDao: LatestDao,
    private val categoryDao: CategoryDao,
) : Cache {
    override fun getPopularDrinks(): Flowable<List<CachedPopular>> {
        return popularDao.getAllPopularDrinks()
    }

    override suspend fun storePopularDrinks(drinks: List<CachedPopular>) {
        popularDao.insertPopularDrinks(drinks)
    }

    override fun getLatestDrinks(): Flowable<List<CachedLatest>> {
        return latestDao.getAllLatestDrinks()
    }

    override suspend fun storeLatestDrinks(drinks: List<CachedLatest>) {
        return latestDao.insertLatestDrinks(drinks)
    }

    init {
        initDatabase()
    }

    private fun initDatabase() {
        GlobalScope.launch {
            // Prepopulate categories
            val categories = CategoryDbModel.DEFAULT_CATEGORIES.toTypedArray()
            val dbCategories = categoryDao.getAllSync()
            if (dbCategories.isNullOrEmpty()) {
                categoryDao.insertAll(*categories)
            }
        }
    }

    override fun getAllCategory(): Flowable<List<CategoryDbModel>> {
        return categoryDao.getAll()
    }

    override fun getAllCategorySync(): List<CategoryDbModel> {
        return categoryDao.getAllSync()
    }

    override fun findCategoryById(id: Long): LiveData<CategoryDbModel> {
        return categoryDao.findById(id)
    }

    override fun findCategoryByIdSync(id: Long): CategoryDbModel {
        return categoryDao.findByIdSync(id)
    }

    override fun searchCocktailsBy(name: String): Flowable<List<CachedLatest>> {
        return latestDao.searchCocktailsBy(name)
    }
}