package com.maku.pombe.popularfeature.domain.usecases

import com.maku.pombe.common.domain.model.NoDrinksException
import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.common.domain.repositories.DrinkRepository
import javax.inject.Inject

class RequestPopularDrinksList @Inject constructor( // 2
    private val popularDrinkRepository: DrinkRepository
) {
    suspend operator fun invoke(): List<PopularDrink> {

        val (drinks) = popularDrinkRepository.requestPopularNetworkData()

        if (drinks.isEmpty()) {
            throw NoDrinksException("No popular drinks :(")
        }
        // caching
        popularDrinkRepository.storePopularDrinks(drinks)
        return drinks
    }
}