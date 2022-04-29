package com.maku.pombe.common.presentation.model.mappers

import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink
import javax.inject.Inject

class UiPopularDrinkMapper @Inject constructor(): UiMapper<PopularDrink, UIPopularDrink> {

  override fun mapToView(input: PopularDrink): UIPopularDrink {
    return UIPopularDrink(
        id = input.idDrink,
        name = input.strDrink,
        photo = input.strDrinkThumb,
        details = input.details
    )
  }
}
