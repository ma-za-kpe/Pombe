package com.maku.pombe.common.presentation.model

import com.maku.pombe.common.domain.model.shared.Details

interface UiDrinks{
    val id: String
    val name: String
    val alcoholic: String?
    val category: String
    val photo: String
}

interface UiDrinkDetails{
    val id: String
    val name: String
    val alcoholic: String?
    val category: String
    val photo: String
    val details: Details
}