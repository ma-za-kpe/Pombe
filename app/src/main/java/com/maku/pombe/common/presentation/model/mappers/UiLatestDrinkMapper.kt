package com.maku.pombe.common.presentation.model.mappers

import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.presentation.model.latest.UILatestDrink
import javax.inject.Inject

class UiLatestDrinkMapper @Inject constructor(): UiMapper<LatestDrink, UILatestDrink> {

  override fun mapToView(input: LatestDrink): UILatestDrink {
    return UILatestDrink(
        id = input.idDrink,
        name = input.strDrink,
        alcoholic = input.strAlcoholic.toString(),
        category = input.strCategory,
        photo = input.strDrinkThumb,
    )
  }
}

