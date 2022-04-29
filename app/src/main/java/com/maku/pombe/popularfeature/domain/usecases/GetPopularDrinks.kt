package com.maku.pombe.popularfeature.domain.usecases

import com.maku.pombe.common.domain.repositories.DrinkRepository
import javax.inject.Inject

class GetPopularDrinks @Inject constructor(
    private val popularDrinkRepository: DrinkRepository
) {
    operator fun invoke() = popularDrinkRepository.getPopularDrinks()
        .filter { it.isNotEmpty() }
}