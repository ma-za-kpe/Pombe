package com.maku.pombe.category

import com.maku.pombe.common.presentation.Event
import com.maku.pombe.common.presentation.model.category.UIDrinkCategory
import com.maku.pombe.common.presentation.model.latest.UILatestDrink
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink

// this is the class that stores the current state of your View.
data class DrinkCategoryViewState(
    val loading: Boolean = true,
    val categories: List<UIDrinkCategory> = emptyList(),
    // Using Event prevents your app from handling the error more than once.
    val failure: Event<Throwable>? = null
)