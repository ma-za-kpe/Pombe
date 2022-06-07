package com.maku.pombe

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maku.pombe.searchfeature.presentation.SearchEvent
import com.maku.pombe.searchfeature.presentation.SearchViewModel
import com.maku.pombe.ui.MainDestinations
import com.maku.pombe.ui.appdrawer.AppDrawer
import com.maku.pombe.ui.components.common.MainAppBar
import com.maku.pombe.ui.rememberPombeAppState
import com.maku.pombe.ui.screens.*
import com.maku.pombe.ui.theme.PombeTheme
import kotlinx.coroutines.launch

@Composable
fun PombeApp() {
    PombeTheme {
        AppContent()
    }
}

@Composable
fun AppContent() {
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val modifier = Modifier

    // val searchWidgetState by searchViewModel.state.observeAsState()
    // Pass this state down, such that we can get top abb bar state from here and remove it from the
    // pombe app state
    val searchTextState by searchViewModel.state.observeAsState()

    val appState = rememberPombeAppState()

    com.maku.logging.Logger.d("top bar state pombe app ${appState.searchViewModel}")
    Crossfade(targetState = MainRouter.currentScreen) { screenState: MutableState<BottomMainScreens> ->
        Scaffold(
            topBar = {
                MainAppBar(
                    searchWidgetState = searchViewModel.state.value!!.topAppBarState,
                    searchTextState = searchTextState!!.searchWidgetText,
                    onTextChange = {
                        searchViewModel.updateSearchTextState(newValue = it)
                        searchViewModel.onEvent(SearchEvent.QueryInput(it))
                    },
                    onCloseClicked = {
                        appState.coroutineScope.launch {
                            searchViewModel.updateTopBarWidgetState(newValue = "CLOSED")
                            searchViewModel.updateSearchTextState(newValue = "")
                            // go back to main screen
                            appState.upPress()
                        }
                    },
                    onSearchClicked = {
                        appState.coroutineScope.launch {
                            searchViewModel.onEvent(SearchEvent.QueryInput(it))
                        }
                    },
                    onIconClicked = {
                        appState.coroutineScope.launch {
                            if (it == "search"){
                                // got to search screen
                                appState.navController.navigate("search")
                                searchViewModel.updateTopBarWidgetState(newValue = "OPENED")
                            } else {
                                // like this drink item
                            }
                        }
                    },
                    screenState.value.route,
                    appState,
                    goBack = {
                        appState.coroutineScope.launch {
                            searchViewModel.updateTopBarWidgetState(newValue = "CLOSED")
                            appState.upPress()
                        }
                    },
                    onLeadingIconClicked = {
                        appState.coroutineScope.launch {
                            if (it == "search"){
                                // open drawer because we are in the home screen
                                appState.coroutineScope.launch { appState.scaffoldState.drawerState.open() }
                            } else {
                                // go back
                                searchViewModel.updateTopBarWidgetState(newValue = "CLOSED")
                                appState.upPress()
                            }
                        }
                    }
                )
            },
            drawerContent = {
                AppDrawer(
                    closeDrawerAction = { appState.coroutineScope.launch { appState.scaffoldState.drawerState.close() } }
                )
            },
            scaffoldState = appState.scaffoldState,
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    BottomNavigationComponent(
                        tabs = appState.bottomBarTabs,
                        currentRoute = appState.currentRoute!!,
                        navigateToRoute = appState::navigateToBottomBarRoute,
                        screenState = screenState
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = appState.navController,
                startDestination = MainDestinations.MAIN_ROUTE.title,
                modifier = modifier.padding(innerPadding),
            ) {
                pombeNavGraph(
                    appState.navController,
                    upPress = appState::upPress,
                    onItemClick = appState::navigateToDrinkDetail,
                )
            }
        }
    }
}

private fun NavGraphBuilder.pombeNavGraph(
    navController: NavHostController,
    upPress: () -> Unit,
    onItemClick: (String, NavBackStackEntry) -> Unit,
) {
    navigation(
        route = MainDestinations.MAIN_ROUTE.title,
        startDestination = BottomMainScreens.HomeScreen.route
    ) {
        addBottomMainGraph(navController, onItemClick)
    }
    composable(MainDestinations.SEARCH.title) { SearchScreen(navController) }

    composable(
        "${MainDestinations.DRINK_DETAIL_ROUTE.title}/{${MainDestinations.DRINK_ID_KEY.title}}",
        arguments = listOf(navArgument(MainDestinations.DRINK_ID_KEY.title) { type = NavType.StringType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val drinkId = arguments.getString(MainDestinations.DRINK_ID_KEY.title)
        DrinkDetail(drinkId.toString(), upPress)
    }
}

@Composable
@Preview
fun AppContentPreview() {
    AppContent()
}
