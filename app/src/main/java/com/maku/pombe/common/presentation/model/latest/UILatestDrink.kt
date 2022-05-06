package com.maku.pombe.common.presentation.model.latest

import com.maku.pombe.common.domain.model.shared.Details

data class UILatestDrink(
    val id: String,
    val name: String,
    val alcoholic: String?,
    val category: String,
    val photo: String,
    val details: Details
)
