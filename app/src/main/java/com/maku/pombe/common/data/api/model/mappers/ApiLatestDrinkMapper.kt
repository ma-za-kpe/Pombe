package com.maku.pombe.common.data.api.model.mappers

import com.maku.pombe.common.data.api.model.latest.DrinkLatest
import com.maku.pombe.common.domain.model.latest.LatestDrink
import com.maku.pombe.common.domain.model.shared.Details
import com.maku.pombe.common.domain.model.shared.Ingredients
import com.maku.pombe.common.domain.model.shared.Instructions
import com.maku.pombe.common.domain.model.shared.Measures
import javax.inject.Inject

class ApiLatestDrinkMapper @Inject constructor(
): ApiMapper<DrinkLatest, LatestDrink> {
    override fun mapToDomain(apiEntity: DrinkLatest): LatestDrink {
        return LatestDrink(
            idDrink = apiEntity.idDrink ?: throw MappingException("Latest drink ID cannot be null"),
            strCategory = apiEntity.strCategory ?: "empty",
            strAlcoholic = apiEntity.strAlcoholic.orEmpty(),
            strCreativeCommonsConfirmed = apiEntity.strCreativeCommonsConfirmed.orEmpty(),
            strDrink = apiEntity.strDrink.orEmpty(),
            strDrinkAlternate = apiEntity.strDrinkAlternate.toString(),
            strDrinkThumb = apiEntity.strDrinkThumb.orEmpty(),
            strGlass = apiEntity.strGlass.orEmpty(),
            strIBA = apiEntity.strIBA.orEmpty(),
            strImageAttribution = apiEntity.strImageAttribution.orEmpty(),
            strImageSource = apiEntity.strImageSource.orEmpty(),
            strTags = apiEntity.strTags.orEmpty(),
            strVideo = apiEntity.strVideo.toString(),
            details = parseDetails(apiEntity),
            dateModified = apiEntity.dateModified.orEmpty() // throws exception if empty
        )
    }

    private fun parseDetails(apiEntity: DrinkLatest): Details {
        return Details(
//            alcoholic = apiEntity.strAlcoholic,
//            commons = apiEntity.strCreativeCommonsConfirmed,
            ingredients = mapIngredients(
                apiEntity.strIngredient1,
                apiEntity.strIngredient2,
                apiEntity.strIngredient3,
                apiEntity.strIngredient4,
                apiEntity.strIngredient5,
                apiEntity.strIngredient6,
                apiEntity.strIngredient7,
                apiEntity.strIngredient8,
                apiEntity.strIngredient9,
                apiEntity.strIngredient10,
                apiEntity.strIngredient11,
                apiEntity.strIngredient12,
                apiEntity.strIngredient13,
                apiEntity.strIngredient14,
                apiEntity.strIngredient15
            ),
            instructions = mapInstructions(
                apiEntity.strInstructions,
                apiEntity.strInstructionsDE,
                apiEntity.strInstructionsES,
                apiEntity.strInstructionsFR,
                apiEntity.strInstructionsIT,
                apiEntity.strInstructionsZHHANS,
                apiEntity.strInstructionsZHHANT
            ),
            measures = mapMeasures(
                apiEntity.strMeasure1,
                apiEntity.strMeasure2,
                apiEntity.strMeasure3,
                apiEntity.strMeasure4,
                apiEntity.strMeasure5,
                apiEntity.strMeasure6,
                apiEntity.strMeasure7,
                apiEntity.strMeasure8,
                apiEntity.strMeasure9,
                apiEntity.strMeasure10,
                apiEntity.strMeasure11,
                apiEntity.strMeasure12,
                apiEntity.strMeasure13,
                apiEntity.strMeasure14,
                apiEntity.strMeasure15,

            )
        )
    }

    private fun mapMeasures(
        strMeasure1: String?,
        strMeasure2: String?,
        strMeasure3: String?,
        strMeasure4: String?,
        strMeasure5: String?,
        strMeasure6: String?,
        strMeasure7: String?,
        strMeasure8: String?,
        strMeasure9: String?,
        strMeasure10: String?,
        strMeasure11: String?,
        strMeasure12: String?,
        strMeasure13: String?,
        strMeasure14: String?,
        strMeasure15: String?
    ): List<Measures> {
        return listOf(
            Measures(
                strMeasure1 = strMeasure1.orEmpty(),
                strMeasure2 = strMeasure2.orEmpty(),
                strMeasure3 = strMeasure3.orEmpty(),
                strMeasure4 = strMeasure4.orEmpty(),
                strMeasure5 = strMeasure5.orEmpty(),
                strMeasure6 = strMeasure6.orEmpty(),
                strMeasure7 = strMeasure7.orEmpty(),
                strMeasure8 = strMeasure8.orEmpty(),
                strMeasure9 = strMeasure9.orEmpty(),
                strMeasure10 = strMeasure10.orEmpty(),
                strMeasure11 = strMeasure11.orEmpty(),
                strMeasure12 = strMeasure12.orEmpty(),
                strMeasure13 = strMeasure13.orEmpty(),
                strMeasure14 = strMeasure14.orEmpty(),
                strMeasure15 = strMeasure15.orEmpty(),
            )
        )
    }

    private fun mapInstructions(
        strInstructions: String?,
        strInstructionsDE: String?,
        strInstructionsES: String?,
        strInstructionsFR: String?,
        strInstructionsIT: String?,
        strInstructionsZHHANS: String?,
        strInstructionsZHHANT: String?
    ): List<Instructions> {
        return listOf(
            Instructions(
                strInstructions = strInstructions.orEmpty(),
                strInstructionsDE = strInstructionsDE.orEmpty(),
                strInstructionsES = strInstructionsES.orEmpty(),
                strInstructionsFR = strInstructionsFR.orEmpty(),
                strInstructionsIT = strInstructionsIT.orEmpty(),
                strInstructionsZHHANS = strInstructionsZHHANS.orEmpty(),
                strInstructionsZHHANT = strInstructionsZHHANT.orEmpty()
            )
        )
    }

    private fun mapIngredients(
        strIngredient1: String?,
        strIngredient2: String?,
        strIngredient3: String?,
        strIngredient4: String?,
        strIngredient5: String?,
        strIngredient6: String?,
        strIngredient7: String?,
        strIngredient8: String?,
        strIngredient9: String?,
        strIngredient10: String?,
        strIngredient11: String?,
        strIngredient12: String?,
        strIngredient13: String?,
        strIngredient14: String?,
        strIngredient15: String?
    ): List<Ingredients> {
        return listOf(
            Ingredients(
                strIngredient1 = strIngredient1.orEmpty(),
                strIngredient2 = strIngredient2.orEmpty(),
                strIngredient3 = strIngredient3.orEmpty(),
                strIngredient4 = strIngredient4.orEmpty(),
                strIngredient5 = strIngredient5.orEmpty(),
                strIngredient6 = strIngredient6.toString(),
                strIngredient7 = strIngredient7.toString(),
                strIngredient8 = strIngredient8.toString(),
                strIngredient9 = strIngredient9.orEmpty(),
                strIngredient10 = strIngredient10.orEmpty(),
                strIngredient11 = strIngredient11.orEmpty(),
                strIngredient12 = strIngredient12.orEmpty(),
                strIngredient13 = strIngredient13.orEmpty(),
                strIngredient14 = strIngredient14.orEmpty(),
                strIngredient15= strIngredient15.orEmpty()

            )
        )
    }

//    private fun parseCommonsConfirmed(strCreativeCommonsConfirmed: String?): Commons? {
//        if (strCreativeCommonsConfirmed.isNullOrEmpty()) return null
//
//        // will throw IllegalStateException if the string does not match String? enum value
//        return Commons.valueOf(strCreativeCommonsConfirmed.uppercase(Locale.ROOT))
//    }
//
//    private fun parseAlcoholic(strAlcoholic: String?): Alcoholic? {
//        if (strAlcoholic.isNullOrEmpty()) return null
//
//        // will throw IllegalStateException if the string does not match String? enum value
//        return Alcoholic.valueOf(strAlcoholic.uppercase(Locale.ROOT))
//    }
    
}
