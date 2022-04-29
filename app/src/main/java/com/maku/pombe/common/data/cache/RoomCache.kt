package com.maku.pombe.common.data.cache

import com.maku.pombe.common.data.cache.daos.LatestDao
import com.maku.pombe.common.data.cache.daos.PopularDao
import com.maku.pombe.common.data.cache.model.cachedLatest.CachedLatest
import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import io.reactivex.Flowable
import javax.inject.Inject


class RoomCache @Inject constructor(
    private val popularDao: PopularDao,
    private val latestDao: LatestDao
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

}