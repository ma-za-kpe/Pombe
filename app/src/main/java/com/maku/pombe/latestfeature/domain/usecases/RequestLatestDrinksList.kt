package com.maku.pombe.latestfeature.domain.usecases

import com.maku.logging.Logger
import com.maku.pombe.common.domain.model.NoDrinksException
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.common.domain.repositories.DrinkRepository
import javax.inject.Inject

class RequestLatestDrinksList @Inject constructor(
    private val drinkRepository: DrinkRepository
) {
    suspend operator fun invoke(): List<LatestDrink> {

        val (drinks) = drinkRepository.requestLatestNetworkData()

        if (drinks.isEmpty()) {
            throw NoDrinksException("No latest drinks :(")
        }

        // caching
        drinkRepository.storeLatestDrinks(drinks)
        return drinks
    }
}