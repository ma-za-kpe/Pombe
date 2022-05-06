package com.maku.pombe.common.domain.repositories

import com.maku.pombe.common.domain.model.latest.LatestDomainResponse
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.domain.model.popular.PopularDomainResponse
import com.maku.pombe.common.domain.model.popular.PopularDrink
import io.reactivex.Flowable

interface DrinkRepository {
  /**
   * Returns the Flowable that emits when the database updates
   * */
  fun getPopularDrinks(): Flowable<List<PopularDrink>>
  /**
   * Calls the API to get Cocktails
   * */
  suspend fun requestPopularNetworkData(): PopularDomainResponse
  /**
   * Stores a list of Cocktails in the database.
   * */
  suspend fun storePopularDrinks(drinks: List<PopularDrink>)

  fun getLatestDrinks(): Flowable<List<LatestDrink>>
  suspend fun requestLatestNetworkData(): LatestDomainResponse
  suspend fun storeLatestDrinks(drinks: List<LatestDrink>)
}