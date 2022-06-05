package com.maku.pombe.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.maku.pombe.R

fun NavGraphBuilder.addBottomMainGraph(
    navController: NavHostController,
    onItemClick: (String, NavBackStackEntry) -> Unit,
    ) {
    composable(BottomMainScreens.HomeScreen.route) { from ->
        HomeScreen(navController,
            onItemClick = { id -> run {
                onItemClick(id, from)
            }
          }
        )
    }

    composable(BottomMainScreens.FavoritesScreen.route) {
        FavoritesScreen(navController)
    }
}

enum class BottomMainScreens(
    val route: String,
    var icon: Int,
    @StringRes val resourceId: Int
) {
    HomeScreen("main/home", R.drawable.ic_home_black_24dp, R.string.home),
    FavoritesScreen("main/favorites", R.drawable.ic_fav_blac, R.string.favorite),
}

object MainRouter {
    var currentScreen: MutableState<BottomMainScreens> = mutableStateOf(
        BottomMainScreens.HomeScreen
    )
}

@Composable
fun BottomNavigationComponent(
    tabs: Array<BottomMainScreens>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    screenState: MutableState<BottomMainScreens>
) {

    val currentSection = tabs.first { it.route == currentRoute }

    BottomNavigation {
        tabs.forEach { screen ->
            val selected = screen == currentSection
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = screen.icon),
                        contentDescription = screen.route
                    )
                },
                label = { Text(text = screen.route, fontSize = 9.sp) },
                selected = selected,
                alwaysShowLabel = true,
                onClick = {
                    navigateToRoute(screen.route)
                    screenState.value = screen
                }
            )
        }
    }
}



