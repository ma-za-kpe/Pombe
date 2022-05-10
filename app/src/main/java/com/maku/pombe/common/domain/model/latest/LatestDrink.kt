package com.maku.pombe.common.domain.model.latest

import com.maku.pombe.common.domain.model.shared.Details

data class LatestDrink(
    val idDrink: String,
    val strCategory: String,
    val strAlcoholic: String?,
    val strCreativeCommonsConfirmed: String?,
    val strDrink: String,
    val strDrinkAlternate: String,
    val strDrinkThumb: String,
    val strGlass: String,
    val strIBA: String,
    val strImageAttribution: String,
    val strImageSource: String,
    val strTags: String,
    val strVideo: String,
    val details: Details,
    val dateModified: String
    )
