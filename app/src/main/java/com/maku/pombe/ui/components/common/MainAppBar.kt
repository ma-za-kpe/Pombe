package com.maku.pombe.ui.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import com.maku.pombe.R
import com.maku.pombe.ui.PombeAppState
import com.maku.pombe.ui.screens.SearchAppBar

@Composable
fun MainAppBar(
    searchWidgetState: String,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onIconClicked: (String) -> Unit,
    route: String,
    appState: PombeAppState,
    goBack: () -> Unit,
    onLeadingIconClicked: (String) -> Unit
) {
    when (searchWidgetState) {
        "CLOSED" -> {
            appState.TopAppBar(
                onIconClicked = {onIconClicked("search")} ,
                title = route,
                Icons.Filled.AccountCircle,
                R.string.account,
                Icons.Filled.Search,
                R.string.search_cocktail,
                onLeadingIconClicked = {onLeadingIconClicked("search")}
            )
        }
        "OPENED" -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked,
                goBack = goBack
            )
        }
        "NOBAR" -> {
            appState.TopAppBar(
                onIconClicked = {onIconClicked("detail")} ,
                title = "",
                Icons.Filled.ArrowBack,
                R.string.account,
                Icons.Outlined.FavoriteBorder,
                R.string.fav_icon,
                onLeadingIconClicked = {onLeadingIconClicked("detail")}
            )
        }
    }
}
