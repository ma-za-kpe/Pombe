package com.maku.pombe.common.data.cache.model.cachedCategory.dbmapper

import com.maku.pombe.common.data.cache.model.cachedCategory.CategoryDbModel
import com.maku.pombe.common.domain.model.category.CategoryModel

interface DbMapper {

  // CategoryDbModel -> CategoryModel

  fun mapCategories(categoryDbModels: List<CategoryDbModel>): List<CategoryModel>

  fun mapCategory(categoryDbModel: CategoryDbModel): CategoryModel


  // CategoryModel -> CategoryDbModel

  fun mapDbCategories(categories: List<CategoryModel>): List<CategoryDbModel>

  fun mapDbCategory(category: CategoryModel): CategoryDbModel
}
