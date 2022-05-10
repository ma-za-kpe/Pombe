package com.maku.pombe.category.usecases

import com.maku.pombe.common.domain.repositories.DrinkRepository
import javax.inject.Inject

class GetDrinksCategory @Inject constructor(
    private val drinkRepository: DrinkRepository
) {
    operator fun invoke() = drinkRepository.getAllCategory()
}