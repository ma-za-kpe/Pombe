package com.maku.pombe.ui.routing

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.maku.pombe.R

sealed class Screen(val route: String, var icon: Int, @StringRes val resourceId: Int) {
  object Home : Screen("home", R.drawable.ic_home_black_24dp, R.string.home)
  object Favorites : Screen("favorites", R.drawable.ic_fav_blac, R.string.favorite)
  object MyProfile : Screen("profile", R.drawable.ic_profile, R.string.my_profile)
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