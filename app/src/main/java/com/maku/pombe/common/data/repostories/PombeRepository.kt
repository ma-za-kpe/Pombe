package com.maku.pombe.common.data.repostories

import com.maku.pombe.common.data.api.ApiParameters
import com.maku.pombe.common.data.api.PombeApi
import com.maku.pombe.common.data.api.model.mappers.ApiPopularDrinkMapper
import com.maku.pombe.common.data.cache.Cache
import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import com.maku.pombe.common.domain.model.NetworkException
import com.maku.pombe.common.domain.model.popular.PopularDomainResponse
import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.common.domain.repositories.PopularDrinkRepository
import io.reactivex.Flowable
import retrofit2.HttpException
import javax.inject.Inject

// This is a boundary between layers, hence the mapper
class PombeRepository @Inject constructor(
    private val api: PombeApi, // 2
    private val cache: Cache,
    private val apiPopularDrinkMapper: ApiPopularDrinkMapper
): PopularDrinkRepository {
    override fun getPopularDrinks(): Flowable<List<PopularDrink>> {
        return cache.getPopularDrinks()
            .distinctUntilChanged() //  This is important because it ensures only events with new
            // information get to the subscriber.
            .map { drinkList ->
                drinkList.map { it.toDomain()}
            }
    }

    override suspend fun requestPopularNetworkData(key: Int): PopularDomainResponse {
        try {
            val (apiPopularDrinks) = api.getNearbyAnimals(ApiParameters.KEY.toInt())

            return PopularDomainResponse(
                apiPopularDrinks.map {apiPopularDrinkMapper.mapToDomain(it) },
            )
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun storePopularDrinks(drinks: List<PopularDrink>) {
        val pombez = drinks.map { CachedPopular.fromDomain(it) }
        cache.storePopularDrinks(pombez)
    }
}