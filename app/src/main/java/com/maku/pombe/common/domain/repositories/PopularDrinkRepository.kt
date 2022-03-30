package com.maku.pombe.common.domain.repositories

import com.maku.pombe.common.data.api.model.Drink
import com.maku.pombe.common.data.api.model.PopularResponse
import com.maku.pombe.common.domain.model.popular.PopularDomainResponse
import com.maku.pombe.common.domain.model.popular.PopularDrink
import io.reactivex.Flowable

interface PopularDrinkRepository {
  fun getPopularDrinks(): Flowable<List<PopularDrink>>
  suspend fun requestPopularNetworkData(key: Int): PopularDomainResponse
  suspend fun storePopularDrinks(drinks: List<PopularDrink>)

//  TODO: Uncomment for remote search
//  suspend fun searchAnimalsRemotely(
//      pageToLoad: Int,
//      searchParameters: SearchParameters,
//      numberOfItems: Int
//  ): PaginatedAnimals
}