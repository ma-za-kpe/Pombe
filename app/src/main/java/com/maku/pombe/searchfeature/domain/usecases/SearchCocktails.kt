package com.maku.pombe.searchfeature.domain.usecases

import com.maku.pombe.common.domain.repositories.DrinkRepository
import com.maku.pombe.searchfeature.domain.models.SearchResults
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchCocktails @Inject constructor(private val drinkRepository: DrinkRepository) {

  operator fun invoke(
      querySubject: BehaviorSubject<String>
  ): Flowable<SearchResults> {

    val query = querySubject
        .debounce(500L, TimeUnit.MILLISECONDS) // this is for SearchScreen.kt line 78
        .map { it.trim() }
        .filter { it.length >= 2 }

    return query.toFlowable(BackpressureStrategy.LATEST)
        .switchMap {
            drinkRepository.searchCachedCocktailsBy(it)
        }
  }
}