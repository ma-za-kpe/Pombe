package com.maku.pombe.latestfeature

// this are actions that the UI triggers
sealed class LatestDrinkEvent {
  object RequestLatestDrinksList: LatestDrinkEvent()
}