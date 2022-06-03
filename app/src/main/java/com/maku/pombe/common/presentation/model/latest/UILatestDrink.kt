package com.maku.pombe.common.presentation.model.latest

import com.maku.pombe.common.domain.model.shared.Details
import com.maku.pombe.common.presentation.model.UiDrinks

data class UILatestDrink(
    override val id: String,
    override val name: String,
    override val alcoholic: String?,
    override val category: String,
    override val photo: String,
    override val details: Details
) : UiDrinks


