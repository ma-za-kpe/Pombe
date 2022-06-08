package com.maku.pombe.common.presentation.model.mappers

import com.maku.pombe.common.data.cache.model.cachedLatest.CachedLatest
import com.maku.pombe.common.domain.model.shared.Details
import com.maku.pombe.common.presentation.model.latest.UILatestDrinkDetails
import javax.inject.Inject

class UiLatestDrinkDetailsMapper @Inject constructor():
  UiMapper<CachedLatest, UILatestDrinkDetails> {
  override fun mapToView(input: CachedLatest): UILatestDrinkDetails {
    return UILatestDrinkDetails(
      id = input.idDrink,
      name = input.strDrink,
      alcoholic = input.strAlcoholic,
      category = input.strCategory,
      photo = input.strDrinkThumb,
      strCreativeCommonsConfirmed = input.strCreativeCommonsConfirmed,
      strDrink = input.strDrink,
      strDrinkAlternate = input.strDrinkAlternate,
      strDrinkThumb = input.strDrinkThumb,
      strGlass = input.strGlass,
      strIBA = input.strIBA,
      strImageAttribution = input.strImageAttribution,
      strImageSource = input.strImageSource,
      strTags = input.strTags,
      strVideo = input.strVideo,
      details = Details(
        input.ingredients,
        input.instructions,
        input.measures
      ),
    )
  }
}