package com.maku.pombe.latestfeature.domain.usecases

import com.maku.pombe.common.domain.repositories.DrinkRepository
import javax.inject.Inject

class GetLatestDrinks @Inject constructor(
    private val drinkRepository: DrinkRepository
) {
    operator fun invoke() = drinkRepository.getLatestDrinks()
        .filter { it.isNotEmpty() }
}