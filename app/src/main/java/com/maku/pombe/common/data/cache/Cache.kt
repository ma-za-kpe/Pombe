package com.maku.pombe.common.data.cache

import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import io.reactivex.Flowable

interface Cache {
    fun getPopularDrinks(): Flowable<List<CachedPopular>>

    suspend fun storePopularDrinks(drinks: List<CachedPopular>)
}