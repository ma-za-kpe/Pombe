package com.maku.pombe.common.domain.model.category

import com.maku.pombe.common.data.cache.model.cachedCategory.CategoryDbModel

const val NEW_CATEGORY_ID = -1L

/**
 * Model class that represents one Category Item
 */
data class CategoryModel(
  val id: Long = NEW_CATEGORY_ID, // This value is used for new categories
  val name: String = ""
) {
  companion object {
    val DEFAULT = with(CategoryDbModel.DEFAULT_CATEGORY) { CategoryModel(id, name) }
  }
}
