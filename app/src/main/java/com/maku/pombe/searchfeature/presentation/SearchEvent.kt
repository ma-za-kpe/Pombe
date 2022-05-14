package com.maku.pombe.searchfeature.presentation

sealed class SearchEvent {
  object PrepareForSearch : SearchEvent()
  object navigateTopAppBar : SearchEvent()
  data class QueryInput(val input: String): SearchEvent()
}

enum class SearchWitchViewState {
  OPENED,
  CLOSED
}