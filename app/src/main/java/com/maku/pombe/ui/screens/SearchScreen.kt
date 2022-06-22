package com.maku.pombe.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.maku.logging.Logger
import com.maku.pombe.common.presentation.Event
import com.maku.pombe.searchfeature.presentation.SearchEvent
import com.maku.pombe.searchfeature.presentation.SearchViewModel
import com.maku.pombe.searchfeature.presentation.SearchViewState
import com.maku.pombe.ui.components.search.SearchItemCard

// code inspiration and source from https://www.youtube.com/watch?v=3oXBnM6fZj0

private const val COLUMN_COUNT = 2
private val GRID_SPACING = 8.dp

@Composable
fun SearchScreen(navController: NavHostController, searchViewModel: SearchViewModel) {
    prepareForSearch(searchViewModel)
    val state = searchViewModel.state.observeAsState()
    Logger.d("search error screen ${state.value}")
    // TODO: handle atleast 2 events
    Surface(modifier = Modifier
        .padding(16.dp)) {
        ObservePopularPombeGridScreenState(state.value)
    }
}

@Composable
private fun HandleFailures(failure: Event<Throwable>?) {
    val context = LocalContext.current
    val unhandledFailure = failure?.getContentIfNotHandled() ?: return

    val fallbackMessage = "An error has occured"
    val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
        fallbackMessage
    }
    else {
        unhandledFailure.message!!
    }

    if (snackbarMessage.isNotEmpty()) {
        // show toats for now, snack bar later in future
        Toast
            .makeText(
                context,
                "Error: $failure",
                Toast.LENGTH_SHORT
            )
            .show()
    }
}

@Composable
fun ObservePopularPombeGridScreenState(value: SearchViewState?) {
    if (value!!.noSearchQuery){
        Text(text ="enter search query")
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(COLUMN_COUNT),
            state = rememberLazyGridState(),
            contentPadding = PaddingValues(
                start = GRID_SPACING,
                end = GRID_SPACING,
                bottom = WindowInsets.navigationBars.getBottom(LocalDensity.current).dp.plus(GRID_SPACING),
            ),
            horizontalArrangement = Arrangement.spacedBy(GRID_SPACING, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(GRID_SPACING, Alignment.CenterVertically),
            content = {
                items(value.searchResults) { drink ->
                    SearchItemCard(
                        drink
                    )
                }
            }
        )
    }
    HandleFailures(value.failure)
}

// get the Subject ready to observe
private fun prepareForSearch(searchViewModel: SearchViewModel) {
    searchViewModel.onEvent(SearchEvent.PrepareForSearch)
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    goBack: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
                Logger.d("Searched Text watch $it")

            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        onTextChange("")
                        onCloseClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Arrow",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {},
        goBack = {}
    )
}