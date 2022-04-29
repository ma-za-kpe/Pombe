package com.maku.pombe.common.presentation.model.mappers

import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.common.presentation.model.latest.UILatestDrink
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink
import javax.inject.Inject

class UiLatestDrinkMapper @Inject constructor(): UiMapper<LatestDrink, UILatestDrink> {

  override fun mapToView(input: LatestDrink): UILatestDrink {
    return UILatestDrink(
        id = input.idDrink,
        name = input.strDrink,
        photo = input.strDrinkThumb,
        details = input.details
    )
  }
}
