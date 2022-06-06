package com.maku.pombe.searchfeature.presentation

import com.maku.pombe.common.presentation.Event
import com.maku.pombe.common.presentation.model.latest.UILatestDrink

// each method copies the original state into a new one associated with the methods name
data class SearchViewState(
    val searchWidgetText: String = "",
    val topAppBarState: String = "CLOSED",
    val noSearchQuery: Boolean = true,
    val searchResults: List<UILatestDrink> = emptyList(),
    val failure: Event<Throwable>? = null
) {

    fun UpdateTopAppBarToSearchable(): SearchViewState {
        return copy(
            searchWidgetText = "",
            topAppBarState = "CLOSED"
        )
    }

    fun updateToNoSearchQuery(): SearchViewState {
        return copy(
            searchWidgetText = "",
            noSearchQuery = true,
            searchResults = emptyList(),
        )
    }

    fun updateToSearching(): SearchViewState {
        return copy(
            noSearchQuery = false
        )
    }

    fun updateToHasSearchResults(drinks: List<UILatestDrink>): SearchViewState {
        return copy(
            noSearchQuery = false,
            searchResults = drinks
        )
    }

    fun updateToHasFailure(throwable: Throwable): SearchViewState {
        return copy(failure = Event(throwable))
    }
}
