package com.maku.pombe.common.presentation.model.popular

import com.maku.pombe.common.domain.model.shared.Details

data class UIPopularDrink(
    val id: String,
    val name: String,
    val photo: String,
    val details: Details
)
