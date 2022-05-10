package com.maku.pombe.ui.components.carousel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.maku.pombe.R
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PombeCarousel(drinks: List<UIPopularDrink>) {
    Column(Modifier
        .fillMaxSize()
    ) {
        val pagerState = rememberPagerState()

        // Display 10 items
        HorizontalPager(
            count = drinks.size,
            state = pagerState,
            // Add 32.dp horizontal padding to 'center' the pages
            contentPadding = PaddingValues(horizontal = 32.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) { page ->
            PagerSampleItem(
                drinks,
                page = page,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }

    }
}

/**
 * Simple pager item which displays an image
 * source: https://github.com/google/accompanist/blob/main/sample/src/main/java/com/google/accompanist/sample/pager/PagerSampleItem.kt
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun PagerSampleItem(
    drinks: List<UIPopularDrink>,
    page: Int,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Box(modifier) {
        // Our page content, displaying a random image
        val image = drinks[page]
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_error_placeholder),
            contentDescription = stringResource(R.string.cocktail_image),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        // Displays the page index
        Text(
            text = page.toString(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(MaterialTheme.colors.surface, RoundedCornerShape(4.dp))
                .sizeIn(minWidth = 40.dp, minHeight = 40.dp)
                .padding(8.dp)
                .wrapContentSize(Alignment.Center)
        )
    }
}