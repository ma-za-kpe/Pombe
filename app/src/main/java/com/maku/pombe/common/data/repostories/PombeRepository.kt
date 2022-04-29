package com.maku.pombe.common.data.repostories

import com.maku.pombe.common.data.api.ApiParameters
import com.maku.pombe.common.data.api.PombeApi
import com.maku.pombe.common.data.api.model.mappers.ApiLatestDrinkMapper
import com.maku.pombe.common.data.api.model.mappers.ApiPopularDrinkMapper
import com.maku.pombe.common.data.cache.Cache
import com.maku.pombe.common.data.cache.model.cachedLatest.CachedLatest
import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import com.maku.pombe.common.domain.model.NetworkException
import com.maku.pombe.common.domain.model.latest.LatestDomainResponse
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.domain.model.popular.PopularDomainResponse
import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.common.domain.repositories.DrinkRepository
import io.reactivex.Flowable
import retrofit2.HttpException
import javax.inject.Inject

// This is a boundary between layers, hence the mapper
class PombeRepository @Inject constructor(
    private val api: PombeApi,
    private val cache: Cache,
    private val apiPopularDrinkMapper: ApiPopularDrinkMapper,
    private val apiLatestDrinkMapper: ApiLatestDrinkMapper
): DrinkRepository {
    override fun getPopularDrinks(): Flowable<List<PopularDrink>> {
        return cache.getPopularDrinks()
            .distinctUntilChanged() //  This is important because it ensures only events with new
            // information get to the subscriber.
            .map { drinkList ->
                drinkList.map { it.toDomain()}
            }
    }

    override suspend fun requestPopularNetworkData(): PopularDomainResponse {
        try {
            val (apiPopularDrinks) = api.getPopularDrinks(ApiParameters.KEY.toInt())

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

    override fun getLatestDrinks(): Flowable<List<LatestDrink>> {
        return cache.getLatestDrinks()
            .distinctUntilChanged()
            .map { drinkList ->
                drinkList.map { it.toDomain()}
            }
    }

    override suspend fun requestLatestNetworkData(): LatestDomainResponse {
        try {
            // get the data from server
            val (apiLatestDrinks) = api.getLatestDrinks(ApiParameters.KEY.toInt())

            // map it to the domain modeled data
            return LatestDomainResponse(
                apiLatestDrinks.map {apiLatestDrinkMapper.mapToDomain(it) },
            )
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun storeLatestDrinks(drinks: List<LatestDrink>) {
        val latestPombe = drinks.map { CachedLatest.fromDomain(it) }
        cache.storeLatestDrinks(latestPombe)
    }
}