package com.maku.pombe.common.data.cache.model.cachedLatest

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.domain.model.shared.Details
import com.maku.pombe.common.domain.model.shared.Ingredients
import com.maku.pombe.common.domain.model.shared.Instructions
import com.maku.pombe.common.domain.model.shared.Measures
import com.maku.pombe.common.utils.DateTimeUtils

@Entity(tableName = "latest")
data class CachedLatest(
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
    val strIngredient1: String,
    val strIngredient10: String,
    val strIngredient11: String,
    val strIngredient12: String,
    val strIngredient13: String,
    val strIngredient14: String,
    val strIngredient15: String,
    val strIngredient2: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: String,
    val strIngredient6: String,
    val strIngredient7: String,
    val strIngredient8: String,
    val strIngredient9: String,
    val strInstructions: String,
    val strInstructionsDE: String,
    val strInstructionsES: String,
    val strInstructionsFR: String,
    val strInstructionsIT: String,
    val strInstructionsZHHANS: String,
    val strInstructionsZHHANT: String,
    val strMeasure1: String,
    val strMeasure10: String,
    val strMeasure11: String,
    val strMeasure12: String,
    val strMeasure13: String,
    val strMeasure14: String,
    val strMeasure15: String,
    val strMeasure2: String,
    val strMeasure3: String,
    val strMeasure4: String,
    val strMeasure5: String,
   val strMeasure6: String,
    val strMeasure7: String,
    val strMeasure8: String,
    val strMeasure9: String,
    val strTags: String,
    val strVideo: String
) {
  companion object {
    fun fromDomain(domainModel: LatestDrink): CachedLatest {
      val details = domainModel.details

      return CachedLatest(
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
          strIngredient1= details.ingredients.strIngredient1.toString(),
          strIngredient10= details.ingredients.strIngredient10.toString(),
          strIngredient11= details.ingredients.strIngredient11.toString(),
          strIngredient12= details.ingredients.strIngredient12.toString(),
          strIngredient13= details.ingredients.strIngredient13.toString(),
          strIngredient14= details.ingredients.strIngredient14.toString(),
          strIngredient15= details.ingredients.strIngredient15.toString(),
          strIngredient2= details.ingredients.strIngredient2.toString(),
          strIngredient3= details.ingredients.strIngredient3.toString(),
          strIngredient4= details.ingredients.strIngredient4.toString(),
          strIngredient5= details.ingredients.strIngredient5.toString(),
          strIngredient6= details.ingredients.strIngredient6.toString(),
          strIngredient7= details.ingredients.strIngredient7.toString(),
          strIngredient8= details.ingredients.strIngredient8.toString(),
          strIngredient9= details.ingredients.strIngredient9.toString(),
          strInstructions= details.instructions.strInstructions.toString(),
          strInstructionsDE= details.instructions.strInstructionsDE.toString(),
          strInstructionsES= details.instructions.strInstructionsES.toString(),
          strInstructionsFR= details.instructions.strInstructionsFR.toString(),
          strInstructionsIT= details.instructions.strInstructionsIT.toString(),
          strInstructionsZHHANS= details.instructions.strInstructionsZHHANS.toString(),
          strInstructionsZHHANT= details.instructions.strInstructionsZHHANT.toString(),
          strMeasure1= details.measures.strMeasure1.toString(),
          strMeasure10= details.measures.strMeasure10.toString(),
          strMeasure11= details.measures.strMeasure11.toString(),
          strMeasure12= details.measures.strMeasure12.toString(),
          strMeasure13= details.measures.strMeasure13.toString(),
          strMeasure14= details.measures.strMeasure14.toString(),
          strMeasure15= details.measures.strMeasure15.toString(),
          strMeasure2= details.measures.strMeasure2.toString(),
          strMeasure3= details.measures.strMeasure3.toString(),
          strMeasure4= details.measures.strMeasure4.toString(),
          strMeasure5= details.measures.strMeasure5.toString(),
          strMeasure6= details.measures.strMeasure6.toString(),
          strMeasure7= details.measures.strMeasure7.toString(),
          strMeasure8= details.measures.strMeasure8.toString(),
          strMeasure9= details.measures.strMeasure9.toString(),
          strTags= domainModel.strTags,
          strVideo= domainModel.strVideo
      )
    }
  }

  fun toDomain(): LatestDrink {
    return LatestDrink(
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
          Ingredients(
              strIngredient1,
                      strIngredient10,
                      strIngredient11,
                      strIngredient12,
                      strIngredient13,
                      strIngredient14,
                      strIngredient15,
                      strIngredient2,
                      strIngredient3,
                      strIngredient4,
                      strIngredient5,
                      strIngredient6,
                      strIngredient7,
                      strIngredient8,
                      strIngredient9

          ),
          Instructions(strInstructions,
                  strInstructionsDE,
                  strInstructionsES,
                  strInstructionsFR,
                  strInstructionsIT,
                  strInstructionsZHHANS,
                  strInstructionsZHHANT
          ),
          Measures(
              strMeasure1,
                  strMeasure2,
                  strMeasure3,
                  strMeasure4,
                  strMeasure5,
                  strMeasure6,
                  strMeasure7,
                  strMeasure8,
                  strMeasure9,
                      strMeasure10,
                      strMeasure11,
                      strMeasure12,
                      strMeasure13,
                      strMeasure14,
                      strMeasure15
          )
      ),
        DateTimeUtils.parse(dateModified)
    )
  }
}


