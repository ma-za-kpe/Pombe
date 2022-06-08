package com.maku.pombe.common.presentation.model

import com.maku.pombe.common.domain.model.shared.Details

interface UiDrinks{
    val id: String
    val name: String
    val alcoholic: String
    val category: String
    val photo: String
}

interface UiDrinkDetails{
    val id: String
    val name: String
    val alcoholic: String
    val category: String
    val photo: String
    val strCreativeCommonsConfirmed: String
    val alcoholic: String?
    val category: String
    val photo: String
    val strCreativeCommonsConfirmed: String?
    val strDrink: String
    val strDrinkAlternate: String
    val strDrinkThumb: String
    val strGlass: String
    val strIBA: String
    val strImageAttribution: String
    val strImageSource: String
    val strTags: String
    val strVideo: String
    val details: Details
}