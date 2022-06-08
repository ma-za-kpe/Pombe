package com.maku.pombe.latestfeature.domain.usecases

import com.maku.pombe.common.domain.repositories.DrinkRepository
import javax.inject.Inject

class GetLatestDrinkById @Inject constructor(
    private val drinkRepository: DrinkRepository
) {
    operator fun invoke(id: String) = drinkRepository.loadSingleLatestDrink(id)
}