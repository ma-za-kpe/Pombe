package com.maku.pombe.common.domain.repositories

import androidx.lifecycle.LiveData
import com.maku.pombe.common.data.cache.model.cachedCategory.CategoryDbModel
import com.maku.pombe.common.domain.model.category.CategoryModel
import com.maku.pombe.common.domain.model.latest.LatestDomainResponse
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.domain.model.popular.PopularDomainResponse
import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.searchfeature.domain.models.SearchParameters
import com.maku.pombe.searchfeature.domain.models.SearchResults
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

  // latest pombe
  fun getLatestDrinks(): Flowable<List<LatestDrink>>
  suspend fun requestLatestNetworkData(): LatestDomainResponse
  suspend fun storeLatestDrinks(drinks: List<LatestDrink>)

  // categories
  fun getAllCategory(): Flowable<List<CategoryModel>>
  fun getAllCategorySync(): List<CategoryModel>
  fun findCategoryById(id: Long): LiveData<CategoryModel>
  fun findCategoryByIdSync(id: Long): CategoryModel

  // search
  fun searchCachedCocktailsBy(searchParameters: String): Flowable<SearchResults>

}