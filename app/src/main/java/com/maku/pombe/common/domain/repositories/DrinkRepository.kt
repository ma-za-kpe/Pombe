package com.maku.pombe.common.domain.repositories

import com.maku.pombe.common.domain.model.latest.LatestDomainResponse
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.domain.model.popular.PopularDomainResponse
import com.maku.pombe.common.domain.model.popular.PopularDrink
import io.reactivex.Flowable

interface DrinkRepository {
  fun getPopularDrinks(): Flowable<List<PopularDrink>>
  suspend fun requestPopularNetworkData(): PopularDomainResponse
  suspend fun storePopularDrinks(drinks: List<PopularDrink>)

  fun getLatestDrinks(): Flowable<List<LatestDrink>>
  suspend fun requestLatestNetworkData(): LatestDomainResponse
  suspend fun storeLatestDrinks(drinks: List<LatestDrink>)

//  TODO: Uncomment for remote search
//  suspend fun searchAnimalsRemotely(
//      pageToLoad: Int,
//      searchParameters: SearchParameters,
//      numberOfItems: Int
//  ): PaginatedAnimals
}