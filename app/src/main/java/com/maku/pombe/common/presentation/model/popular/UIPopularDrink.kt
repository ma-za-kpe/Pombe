package com.maku.pombe.common.presentation.model.popular

import com.maku.pombe.common.domain.model.shared.Details
import com.maku.pombe.common.presentation.model.UiDrinkDetails
import com.maku.pombe.common.presentation.model.UiDrinks

data class UIPopularDrink(
    override val id: String,
    override val name: String,
    override val alcoholic: String,
    override val category: String,
    override val photo: String,
): UiDrinks

data class UIPopularDrinkDetails(
    override val id: String,
    override val name: String,
    override val alcoholic: String,
    override val category: String,
    override val photo: String,
    override val strCreativeCommonsConfirmed: String,
    override val strDrink: String,
    override val strDrinkAlternate: String,
    override val strDrinkThumb: String,
    override val strGlass: String,
    override val strIBA: String,
    override val strImageAttribution: String,
    override val strImageSource: String,
    override val strTags: String,
    override val strVideo: String,
    override val alcoholic: String?,
    override val category: String,
    override val photo: String,
    override val details: Details
) : UiDrinkDetails
