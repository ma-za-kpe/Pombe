package com.maku.pombe.common.presentation.model.mappers

import com.maku.pombe.common.domain.model.category.CategoryModel
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.presentation.model.category.UIDrinkCategory
import com.maku.pombe.common.presentation.model.latest.UILatestDrink
import javax.inject.Inject

class UiDrinkCategoryMapper @Inject constructor(): UiMapper<CategoryModel, UIDrinkCategory> {

  override fun mapToView(input: CategoryModel): UIDrinkCategory {
    return UIDrinkCategory(
        id = input.id,
        name = input.name
    )
  }
}
