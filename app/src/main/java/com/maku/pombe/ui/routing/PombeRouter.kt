package com.maku.pombe.ui.routing

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.maku.pombe.R

sealed class Screen(val titleResId: Int) {
  object Home : Screen(R.string.home)
  object Favorites : Screen(R.string.favorite)
  object MyProfile : Screen(R.string.my_profile)
}

object PombeRouter {
  var currentScreen: MutableState<Screen> = mutableStateOf(
    Screen.Home
  )

  private var previousScreen: MutableState<Screen> = mutableStateOf(
    Screen.Home
  )

  fun navigateTo(destination: Screen) {
    previousScreen.value = currentScreen.value
    currentScreen.value = destination
  }

  fun goBack() {
    currentScreen.value = previousScreen.value
  }
}