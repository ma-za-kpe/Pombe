package com.maku.pombe.ui

import android.content.res.Resources
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maku.pombe.R
import com.maku.pombe.ui.screens.BottomMainScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
/**
 * code inspired and source from https://github.com/android/compose-samples/tree/main/Jetsnack sample app
 * */

/**
 * Destinations used in the [PombeApp].
 */
enum class  MainDestinations(
    val title: String,
) {
   MAIN_ROUTE("main") {},
    SEARCH("search")
}

/**
 * Remembers and creates an instance of [PombeAppState]
 */
@Composable
fun rememberPombeAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) =
    remember(scaffoldState, navController, resources, coroutineScope) {
        PombeAppState(scaffoldState, navController, resources, coroutineScope)
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
) {
    // ----------------------------------------------------------
    // TopBar state source of truth
    // ----------------------------------------------------------
    @Composable
    fun TopAppBar(
        onSearchClicked: () -> Unit,
        title: String
    ) {

        TopAppBar(
            title = {
                Text(
                    text = title,
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    coroutineScope.launch { scaffoldState.drawerState.open() }
                }) {
                    Icon(
                        Icons.Filled.AccountCircle,
                        contentDescription = stringResource(id = R.string.account)
                    )
                }
            },
            actions = {
                IconButton(onSearchClicked) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Pombe"
                    )
                }
            }
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
