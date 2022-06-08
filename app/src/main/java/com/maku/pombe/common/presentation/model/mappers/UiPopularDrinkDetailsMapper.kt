package com.maku.pombe.common.presentation.model.mappers

import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import com.maku.pombe.common.domain.model.shared.Details
import com.maku.pombe.common.presentation.model.popular.UIPopularDrinkDetails
import javax.inject.Inject

class UiPopularDrinkDetailsMapper @Inject constructor():
  UiMapper<CachedPopular, UIPopularDrinkDetails> {
  override fun mapToView(input: CachedPopular): UIPopularDrinkDetails {
    return UIPopularDrinkDetails(
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