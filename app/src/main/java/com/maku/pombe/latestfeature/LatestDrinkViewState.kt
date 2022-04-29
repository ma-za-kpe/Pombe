package com.maku.pombe.latestfeature

import com.maku.pombe.common.presentation.Event
import com.maku.pombe.common.presentation.model.latest.UILatestDrink
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink

// this is the class that stores the current state of your View.
data class LatestDrinkViewState(
    val loading: Boolean = true,
    val drinks: List<UILatestDrink> = emptyList(),
    val noMoreDrinks: Boolean = false,
    // Using Event prevents your app from handling the error more than once.
    val failure: Event<Throwable>? = null
)