package com.maku.pombe.common.data.repostories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.maku.pombe.common.data.api.ApiParameters
import com.maku.pombe.common.data.api.PombeApi
import com.maku.pombe.common.data.api.model.mappers.ApiLatestDrinkMapper
import com.maku.pombe.common.data.api.model.mappers.ApiPopularDrinkMapper
import com.maku.pombe.common.data.cache.Cache
import com.maku.pombe.common.data.cache.model.cachedCategory.dbmapper.DbMapper
import com.maku.pombe.common.data.cache.model.cachedCategory.dbmapper.DbMapperImpl
import com.maku.pombe.common.data.cache.model.cachedLatest.CachedLatest
import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import com.maku.pombe.common.domain.model.NetworkException
import com.maku.pombe.common.domain.model.category.CategoryModel
import com.maku.pombe.common.domain.model.latest.LatestDomainResponse
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.domain.model.popular.PopularDomainResponse
import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.common.domain.repositories.DrinkRepository
import com.maku.pombe.searchfeature.domain.models.SearchResults
import io.reactivex.Flowable
import retrofit2.HttpException
import javax.inject.Inject

// This is a boundary between layers, hence the mapper
class PombeRepository @Inject constructor(
    private val api: PombeApi,
    private val cache: Cache,
    private val apiPopularDrinkMapper: ApiPopularDrinkMapper,
    private val apiLatestDrinkMapper: ApiLatestDrinkMapper,
    private val dbMapper: DbMapperImpl
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

            val x =  LatestDomainResponse(
                apiLatestDrinks.map {apiLatestDrinkMapper.mapToDomain(it) },
            )
            // map it to the domain modeled data
            return x
        } catch (exception: HttpException) {
            throw NetworkException(exception.message ?: "Code ${exception.code()}")
        }
    }

    override suspend fun storeLatestDrinks(drinks: List<LatestDrink>) {
        val latestPombe = drinks.map { CachedLatest.fromDomain(it) }
        cache.storeLatestDrinks(latestPombe)
    }

    override fun getAllCategory(): Flowable<List<CategoryModel>> {
        return cache.getAllCategory()
            .distinctUntilChanged() //  This is important because it ensures only events with new
            // information get to the subscriber.
            .map { drinkList ->
                dbMapper.mapCategories(drinkList)
            }
    }

    override fun getAllCategorySync(): List<CategoryModel> = dbMapper.mapCategories(cache.getAllCategorySync())
    override fun findCategoryById(id: Long): LiveData<CategoryModel> =
        Transformations.map(cache.findCategoryById(id)) { dbMapper.mapCategory(it) }

    override fun findCategoryByIdSync(id: Long): CategoryModel = dbMapper.mapCategory(cache.findCategoryByIdSync(id))
    override fun searchCachedCocktailsBy(searchParameters: String): Flowable<SearchResults> {
        return cache.searchCocktailsBy(searchParameters)
            .distinctUntilChanged()
            .map { cocktails ->
                cocktails.map { it.toDomain() }
            }
            .map{ SearchResults(it, searchParameters) }
    }

}