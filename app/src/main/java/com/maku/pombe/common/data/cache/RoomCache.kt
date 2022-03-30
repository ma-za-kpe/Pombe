package com.maku.pombe.common.data.cache

import com.maku.pombe.common.data.cache.daos.PopularDao
import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import io.reactivex.Flowable
import javax.inject.Inject


class RoomCache @Inject constructor(
    private val popularDao: PopularDao
) : Cache {
    override fun getPopularDrinks(): Flowable<List<CachedPopular>> {
        return popularDao.getAllPopularDrinks()
    }

    override suspend fun storePopularDrinks(drinks: List<CachedPopular>) {
        popularDao.insertPopularDrinks(drinks)
    }

}