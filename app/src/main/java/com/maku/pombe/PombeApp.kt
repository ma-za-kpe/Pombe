package com.maku.pombe

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maku.pombe.latestfeature.LatestFragmentViewModel
import com.maku.pombe.popularfeature.presentation.PopularFragmentViewModel
import com.maku.pombe.ui.appdrawer.AppDrawer
import com.maku.pombe.ui.routing.PombeRouter.currentScreen
import com.maku.pombe.ui.routing.Screen
import com.maku.pombe.ui.screens.FavoritesScreen
import com.maku.pombe.ui.screens.HomeScreen
import com.maku.pombe.ui.theme.PombeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PombeApp(
    latestFragmentViewModel: LatestFragmentViewModel,
    popularFragmentViewModel: PopularFragmentViewModel
) {
    PombeTheme {
        AppContent(latestFragmentViewModel, popularFragmentViewModel)
    }
}

@Composable
fun AppContent(
    latestFragmentViewModel: LatestFragmentViewModel,
    popularFragmentViewModel: PopularFragmentViewModel
) {
    val navController = rememberNavController()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    Crossfade(targetState = currentScreen) { screenState: MutableState<Screen> ->
        Scaffold(
            topBar = drawTopBar(
                screenState.value,
                scaffoldState,
                coroutineScope,
                onSearchPombeClick = { }),
            drawerContent = {
                AppDrawer(
                    closeDrawerAction = { coroutineScope.launch { scaffoldState.drawerState.close() } }
                )
            },
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(painterResource(id = screen.icon), contentDescription = screen.route) },
                            label = { Text(text = screen.route, fontSize = 9.sp) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            alwaysShowLabel = true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = Screen.Home.route,
                Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) { HomeScreen(navController, latestFragmentViewModel, popularFragmentViewModel) }
                composable(Screen.Favorites.route) { FavoritesScreen(navController) }
            }
        }
    }
}

val items = listOf(
    Screen.Home,
    Screen.Favorites
)



//@Composable
//fun AppContent() {
//    val scaffoldState: ScaffoldState = rememberScaffoldState()
//    val coroutineScope: CoroutineScope = rememberCoroutineScope()
//    Crossfade(targetState = currentScreen) { screenState: MutableState<Screen> ->
//        Scaffold(
//            topBar = drawTopBar(screenState.value, scaffoldState, coroutineScope, onSearchPombeClick = { }),
//            drawerContent = {
//                AppDrawer(
//                    closeDrawerAction = { coroutineScope.launch { scaffoldState.drawerState.close() } }
//                )
//            },
//            scaffoldState = scaffoldState,
//            bottomBar = {
//                BottomNavigationComponent(screenState = screenState)
//            },
//            content = {
//                MainScreenContainer(
//                    modifier = Modifier.padding(bottom = 56.dp),
//                    screenState = screenState,
//                )
//            }
//        )
//    }
//}

fun drawTopBar(value: Screen, scaffoldState: ScaffoldState, coroutineScope: CoroutineScope, onSearchPombeClick: () -> Unit)
: @Composable (() -> Unit) {
    if (value == Screen.MyProfile) {
        return {}
    } else {
        return { TopAppBar(scaffoldState = scaffoldState, coroutineScope = coroutineScope, onSearchPombeClick) }
    }
}

@Composable
fun TopAppBar(scaffoldState: ScaffoldState, coroutineScope: CoroutineScope,  onSearchPombeClick: () -> Unit
) {

    val colors = MaterialTheme.colors

    TopAppBar(
        title = {
            Text(
                text = stringResource(currentScreen.value.resourceId),
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
            IconButton(onClick = onSearchPombeClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Pombe"
                )
            }
        }
    )
}

@Composable
private fun BottomNavigationComponent(
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>
) {
    var selectedItem by remember { mutableStateOf(0) }
    val colors = MaterialTheme.colors

    val items = listOf(
        NavigationItem(0, R.drawable.ic_home_black_24dp, R.string.home_icon, Screen.Home),
        NavigationItem(1, R.drawable.ic_fav_blac, R.string.fav_icon, Screen.Favorites),
    )
    BottomNavigation(
        modifier = modifier
    ) {
        items.forEach {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = it.vectorResourceId),
                        contentDescription = stringResource(id = it.contentDescriptionResourceId),
                    )
                },
                selected = selectedItem == it.index,
                onClick = {
                    selectedItem = it.index
                    screenState.value = it.screen
                }
            )
        }
    }
}

private data class NavigationItem(
    val index: Int,
    val vectorResourceId: Int,
    val contentDescriptionResourceId: Int,
    val screen: Screen
)

@Composable
private fun MainScreenContainer(
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>
) {
    Surface(
         color = colors.background
    ) {
        when (screenState.value) {
//            Screen.Home -> HomeScreen(navController)
//            Screen.Favorites -> FavoritesScreen(navController)
//            Screen.MyProfile -> MyProfileScreen()
        }
    }
}

//@Composable
//@Preview
//fun AppContentPreview() {
//    AppContent()
//}
