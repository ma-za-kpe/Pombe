package com.maku.pombe.category

// this is an action that the UI triggers
sealed class DrinkCategoryEvent {
  object RequestDrinkCategoryList: DrinkCategoryEvent()
}