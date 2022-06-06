package com.maku.pombe.ui

import android.content.res.Resources
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maku.logging.Logger
import com.maku.pombe.searchfeature.presentation.SearchViewModel
import com.maku.pombe.ui.screens.BottomMainScreens
import kotlinx.coroutines.CoroutineScope

/**
 * code source from https://github.com/android/compose-samples/tree/main/Jetsnack sample app
 * */

/**
 * Destinations used in the [PombeApp].
 */
enum class  MainDestinations(
    val title: String,
) {
    MAIN_ROUTE("main") {},
    SEARCH("search"),
    DRINK_DETAIL_ROUTE("drink"),
    DRINK_ID_KEY("drinkId")

}

/**
 * Remembers and creates an instance of [PombeAppState]
 */
@Composable
fun rememberPombeAppState(
    searchViewModel: SearchViewModel = hiltViewModel(), // find better way to handle this
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) =
    remember(scaffoldState, navController, resources, coroutineScope, searchViewModel.state.value?.topAppBarState) {
        PombeAppState(scaffoldState, navController, resources, coroutineScope, searchViewModel)
    }

/**
 * Responsible for holding state related to [PombeApp] and containing UI-related logic.
 */
@Stable
class PombeAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val resources: Resources,
    val coroutineScope: CoroutineScope,
    val searchViewModel: SearchViewModel,
) {


    // ----------------------------------------------------------
    // TopBar state source of truth
    // ----------------------------------------------------------


    @Composable
    fun TopAppBar(
        onIconClicked: () -> Unit,
        title: String,
        leadIcon: ImageVector,
        leadIconDesc: Int,
        trailingIcon: ImageVector,
        trailingIconDesc: Int,
        onLeadingIconClicked: () -> Unit,
        background: Color,
        contentColor: Color,
        elevation: Dp
    ) {
//  coroutineScope.launch { scaffoldState.drawerState.open() }
        TopAppBar(
            title = {
                Text(
                    text = title,
                )
            },
            navigationIcon = {
                IconButton(onLeadingIconClicked) {
                    Icon(
                        leadIcon,
                        contentDescription = stringResource(id = leadIconDesc)
                    )
                }
            },
            actions = {
                IconButton(onIconClicked) {
                    Icon(
                        trailingIcon,
                        contentDescription = stringResource(id = trailingIconDesc)
                    )
                }
            },
            backgroundColor = background,
            contentColor = contentColor,
            elevation = elevation
        )
    }

    // ----------------------------------------------------------
    // BottomBar state source of truth
    // ----------------------------------------------------------

    val bottomBarTabs = BottomMainScreens.values()
    private val bottomBarRoutes = bottomBarTabs.map { it.route }

    // Reading this attribute will cause recompositions when the bottom bar needs shown, or not.
    // Not all routes need to show the bottom bar.
    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    // ----------------------------------------------------------
    // Navigation state source of truth
    // ----------------------------------------------------------

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        // set top bar state to closed
        searchViewModel.updateTopBarWidgetState("CLOSED")
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                // Pop up backstack to the first destination and save state. This makes going back
                // to the start destination when pressing back in any other bottom tab.
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToDrinkDetail(drinkId: String, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            // TODO: remove this state update from here, and use only in calling screens or we can pass in the state variable for the other screen and use a when statement to determine which screen to navigate to and which top app bar to show
            searchViewModel.updateTopBarWidgetState(newValue = "DETAIL")
            navController.navigate("${MainDestinations.DRINK_DETAIL_ROUTE.title}/$drinkId")
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

/**
 * Copied from similar function in NavigationUI.kt
 *
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:navigation/navigation-ui/src/main/java/androidx/navigation/ui/NavigationUI.kt
 */
private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

/**
 * A composable function that returns the [Resources]. It will be recomposed when `Configuration`
 * gets updated.
 */
@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
