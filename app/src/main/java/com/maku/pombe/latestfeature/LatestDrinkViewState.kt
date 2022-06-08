package com.maku.pombe.latestfeature

import com.maku.pombe.common.domain.model.shared.Details
import com.maku.pombe.common.presentation.Event
import com.maku.pombe.common.presentation.model.latest.UILatestDrink
import com.maku.pombe.common.presentation.model.latest.UILatestDrinkDetails

// this is the class that stores the current state of your View.
data class LatestDrinkViewState(
    val loading: Boolean = true,
    val drinkById: UILatestDrinkDetails = UILatestDrinkDetails(
        "","","", "","",
        Details(emptyList(), emptyList(), emptyList())
    ),
    val drinks: List<UILatestDrink> = emptyList(),
    // Using Event prevents your app from handling the error more than once.
    val failure: Event<Throwable>? = null
)