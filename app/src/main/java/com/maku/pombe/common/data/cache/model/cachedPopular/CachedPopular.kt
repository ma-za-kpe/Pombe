package com.maku.pombe.common.data.cache.model.cachedPopular

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maku.pombe.common.domain.model.popular.PopularDrink
import com.maku.pombe.common.domain.model.shared.*
import com.maku.pombe.common.utils.DateTimeUtils

@Entity(tableName = "popular")
data class CachedPopular(
    @PrimaryKey(autoGenerate = false)
    val idDrink: String,
    val dateModified: String,
    val strAlcoholic: String,
    val strCategory: String,
    val strCreativeCommonsConfirmed: String,
    val strDrink: String,
    val strDrinkAlternate: String,
    val strDrinkThumb: String,
    val strGlass: String,
    val strIBA: String,
    val strImageAttribution: String,
    val strImageSource: String,
    val ingredients: List<Ingredients>,
    val instructions: List<Instructions>,
    val measures: List<Measures>,
    val strTags: String,
    val strVideo: String
) {
  companion object {
    fun fromDomain(domainModel: PopularDrink): CachedPopular {
      val details = domainModel.details

      return CachedPopular(
          idDrink = domainModel.idDrink,
          dateModified = domainModel.dateModified.toString(),
          strAlcoholic = domainModel.strAlcoholic.toString(),
          strCategory = domainModel.strCategory,
          strCreativeCommonsConfirmed = domainModel.strCreativeCommonsConfirmed.toString(),
          strDrink= domainModel.strDrink,
          strDrinkAlternate= domainModel.strDrinkAlternate,
          strDrinkThumb= domainModel.strDrinkThumb,
          strGlass= domainModel.strGlass,
          strIBA= domainModel.strIBA,
          strImageAttribution= domainModel.strImageAttribution,
          strImageSource= domainModel.strImageSource,
        ingredients =details.ingredients,
        instructions = details.instructions,
        measures = details.measures,
          strTags= domainModel.strTags,
          strVideo= domainModel.strVideo
      )
    }
  }

  fun toDomain(): PopularDrink {
    return PopularDrink(
        idDrink,
      strCategory,
        strAlcoholic,
        strCreativeCommonsConfirmed,
      strDrink,
      strDrinkAlternate,
      strDrinkThumb,
      strGlass,
      strIBA,
      strImageAttribution,
      strImageSource,
      strTags,
      strVideo,
      Details(
        ingredients,
        instructions,
        measures,
      ),
        DateTimeUtils.parse(dateModified)
    )
  }
}


