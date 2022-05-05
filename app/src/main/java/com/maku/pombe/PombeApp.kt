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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maku.pombe.latestfeature.LatestFragmentViewModel
import com.maku.pombe.ui.appdrawer.AppDrawer
import com.maku.pombe.ui.routing.PombeRouter.currentScreen
import com.maku.pombe.ui.routing.Screen
import com.maku.pombe.ui.screens.FavoritesScreen
import com.maku.pombe.ui.screens.HomeScreen
import com.maku.pombe.ui.screens.MyProfileScreen
import com.maku.pombe.ui.theme.PombeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PombeApp() {
    PombeTheme {
        AppContent()
    }
}

@Composable
fun AppContent() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    Crossfade(targetState = currentScreen) { screenState: MutableState<Screen> ->
        Scaffold(
            topBar = drawTopBar(screenState.value, scaffoldState, coroutineScope, onSearchPombeClick = { }),
            drawerContent = {
                AppDrawer(
                    closeDrawerAction = { coroutineScope.launch { scaffoldState.drawerState.close() } }
                )
            },
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigationComponent(screenState = screenState)
            },
            content = {
                MainScreenContainer(
                    modifier = Modifier.padding(bottom = 56.dp),
                    screenState = screenState,
                )
            }
        )
    }
}

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
                text = stringResource(currentScreen.value.titleResId),
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
            Screen.Home -> HomeScreen()
            Screen.Favorites -> FavoritesScreen()
            Screen.MyProfile -> MyProfileScreen()
        }
    }
}

@Composable
@Preview
fun AppContentPreview() {
    AppContent()
}
