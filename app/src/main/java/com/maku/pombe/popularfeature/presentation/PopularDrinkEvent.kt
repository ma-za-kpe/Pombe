package com.maku.pombe.popularfeature.presentation

// this are actions that the UI triggers
sealed class PopularDrinkEvent {
  object RequestPopularDrinksList: PopularDrinkEvent()
}