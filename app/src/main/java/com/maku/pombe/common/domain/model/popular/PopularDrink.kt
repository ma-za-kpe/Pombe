package com.maku.pombe.common.domain.model.popular

import com.maku.pombe.common.domain.model.shared.Details

data class PopularDrink(
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
    val dateModified: org.threeten.bp.LocalDateTime
    )
