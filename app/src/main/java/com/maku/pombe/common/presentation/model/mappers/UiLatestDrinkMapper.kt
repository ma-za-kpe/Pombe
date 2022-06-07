package com.maku.pombe.common.presentation.model.mappers

import com.maku.pombe.common.data.cache.model.cachedLatest.CachedLatest
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.common.domain.model.shared.Details
import com.maku.pombe.common.presentation.model.latest.UILatestDrink
import com.maku.pombe.common.presentation.model.latest.UILatestDrinkDetails
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink
import com.maku.pombe.common.presentation.model.popular.UIPopularDrinkDetails
import javax.inject.Inject

class UiLatestDrinkMapper @Inject constructor(): UiMapper<LatestDrink, UILatestDrink> {

  override fun mapToView(input: LatestDrink): UILatestDrink {
    return UILatestDrink(
        id = input.idDrink,
        name = input.strDrink,
        alcoholic = input.strAlcoholic,
        category = input.strCategory,
        photo = input.strDrinkThumb,
    )
  }
}

class UiLatestDrinkDetailsMapper @Inject constructor(): UiMapper<CachedLatest, UILatestDrinkDetails> {
  override fun mapToView(input: CachedLatest): UILatestDrinkDetails {
    return UILatestDrinkDetails(
      id = input.idDrink,
      name = input.strDrink,
      alcoholic = input.strAlcoholic,
      category = input.strCategory,
      photo = input.strDrinkThumb,
      details = Details(
        input.ingredients,
        input.instructions,
        input.measures
      )
    )
  }
}
