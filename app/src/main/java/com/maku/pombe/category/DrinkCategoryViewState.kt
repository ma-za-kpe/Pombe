package com.maku.pombe.category

import com.maku.pombe.common.presentation.Event
import com.maku.pombe.common.presentation.model.category.UIDrinkCategory
import com.maku.pombe.common.presentation.model.latest.UILatestDrink
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink
import com.maku.pombe.searchfeature.presentation.SearchViewState

// this is the class that stores the current state of your View.
data class DrinkCategoryViewState(
    val selected: Boolean = false,
    val loading: Boolean = true,
    val selectedCategory: String = "All",
    val categories: List<UIDrinkCategory> = emptyList(),
    val failure: Event<Throwable>? = null
) {
    fun setSelectedCategory(name: String, selected: Boolean): DrinkCategoryViewState {
        return copy(
            selectedCategory = name,
            selected = selected
        )
    }
}