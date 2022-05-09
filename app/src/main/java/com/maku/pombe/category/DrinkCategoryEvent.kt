package com.maku.pombe.latestfeature

// this is an action that the UI triggers
sealed class LatestDrinkEvent {
  object RequestLatestDrinksList: LatestDrinkEvent()
}