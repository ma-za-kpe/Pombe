package com.maku.pombe.common.data.cache.model.cachedCategory.dbmapper

import com.maku.pombe.common.data.cache.model.cachedCategory.CategoryDbModel
import com.maku.pombe.common.domain.model.category.CategoryModel
import javax.inject.Inject

class DbMapperImpl @Inject constructor(
): DbMapper {

  override fun mapCategories(categoryDbModels: List<CategoryDbModel>): List<CategoryModel> =
    categoryDbModels.map { mapCategory(it) }

  override fun mapCategory(categoryDbModel: CategoryDbModel): CategoryModel =
    with(categoryDbModel) { CategoryModel(id, name) }


  override fun mapDbCategories(categories: List<CategoryModel>): List<CategoryDbModel> =
    categories.map { mapDbCategory(it) }

  override fun mapDbCategory(category: CategoryModel): CategoryDbModel =
    with(category) { CategoryDbModel(id, name) }

}
