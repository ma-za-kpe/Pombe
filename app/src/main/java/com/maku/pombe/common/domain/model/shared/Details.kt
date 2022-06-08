package com.maku.pombe.common.domain.model.shared

data class Details(
    val ingredients: List<Ingredients>,
    val instructions: List<Instructions>,
    val measures: List<Measures>
)
