package com.maku.pombe.popularfeature.presentation

import com.maku.pombe.common.presentation.Event
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink

// this is the class that stores the current state of your View.
data class PopularDrinkViewState(
    val loading: Boolean = true,
    val drinks: List<UIPopularDrink> = emptyList(),
    val noMoreDrinks: Boolean = false,
    // Using Event prevents your app from handling the error more than once.
    val failure: Event<Throwable>? = null
)