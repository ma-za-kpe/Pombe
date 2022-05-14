package com.maku.pombe

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.maku.logging.Logger
import com.maku.pombe.category.DrinkCategoryViewModel
import com.maku.pombe.latestfeature.LatestFragmentViewModel
import com.maku.pombe.popularfeature.presentation.PopularFragmentViewModel
import com.maku.pombe.searchfeature.presentation.SearchEvent
import com.maku.pombe.searchfeature.presentation.SearchViewModel
import com.maku.pombe.ui.MainDestinations
import com.maku.pombe.ui.appdrawer.AppDrawer
import com.maku.pombe.ui.rememberPombeAppState
import com.maku.pombe.ui.screens.*
import com.maku.pombe.ui.theme.PombeTheme
import kotlinx.coroutines.launch

@Composable
fun PombeApp(
    latestFragmentViewModel: LatestFragmentViewModel,
    popularFragmentViewModel: PopularFragmentViewModel,
    drinkCategoryViewModel: DrinkCategoryViewModel,
    searchViewModel: SearchViewModel
) {
    PombeTheme {
        AppContent(latestFragmentViewModel, popularFragmentViewModel, drinkCategoryViewModel, searchViewModel)
    }
}

@Composable
fun AppContent(
    latestFragmentViewModel: LatestFragmentViewModel,
    popularFragmentViewModel: PopularFragmentViewModel,
    drinkCategoryViewModel: DrinkCategoryViewModel,
    searchViewModel: SearchViewModel
) {

    val searchWidgetState by searchViewModel.state.observeAsState()
    val searchTextState by searchViewModel.state.observeAsState()

    val appState = rememberPombeAppState()
    Crossfade(targetState = MainRouter.currentScreen) { screenState: MutableState<BottomMainScreens> ->
        Scaffold(
            topBar = {
                MainAppBar(
                    searchWidgetState = searchWidgetState!!.topAppBarSearch,
                    searchTextState = searchTextState!!.searchWidgetText,
                    onTextChange = {
                        searchViewModel.updateSearchTextState(newValue = it)
                        searchViewModel.onEvent(SearchEvent.QueryInput(it))
                    },
                    onCloseClicked = {
                        searchViewModel.updateSearchWidgetState(newValue = "CLOSED")
                        searchViewModel.updateSearchTextState(newValue = "")
                        // go back to main screen
                        appState.upPress()
                    },
                    onSearchClicked = {
                        searchViewModel.onEvent(SearchEvent.QueryInput(it))
                    },
                    onSearchTriggered = {
                        // got to search screen
                        appState.navController.navigate("search")
                        searchViewModel.updateSearchWidgetState(newValue = "OPENED")
                    },
                    screenState.value.route,
                    appState,
                    goBack = {
                        appState.upPress()
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
                modifier = Modifier.padding(innerPadding)
            ) {
                pombeNavGraph(
                    appState.navController,
                    upPress = appState::upPress,
                    latestFragmentViewModel,
                    popularFragmentViewModel,
                    drinkCategoryViewModel,
                    searchViewModel
                )
            }
        }
    }
}

private fun NavGraphBuilder.pombeNavGraph(
    navController: NavHostController,
    upPress: () -> Unit,
    latestFragmentViewModel: LatestFragmentViewModel,
    popularFragmentViewModel: PopularFragmentViewModel,
    drinkCategoryViewModel: DrinkCategoryViewModel,
    searchViewModel: SearchViewModel,
) {
    navigation(
        route = MainDestinations.MAIN_ROUTE.title,
        startDestination = BottomMainScreens.HomeScreen.route
    ) {
        addBottomMainGraph(navController, latestFragmentViewModel, popularFragmentViewModel, drinkCategoryViewModel)
    }
      composable(MainDestinations.SEARCH.title) { SearchScreen(navController, searchViewModel) }
}

//@Composable
//@Preview
//fun AppContentPreview() {
//    AppContent()
//}
