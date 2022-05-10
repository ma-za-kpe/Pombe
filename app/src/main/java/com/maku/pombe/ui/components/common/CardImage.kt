package com.maku.pombe.ui.components.common

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.maku.pombe.R
import com.maku.pombe.common.presentation.model.UiDrinks
import com.maku.pombe.common.presentation.model.latest.UILatestDrink

@Composable
fun CardImage(
    modifier: Modifier = Modifier,
    topStart: Dp,
    topEnd: Dp,
    bottomEnd: Dp,
    bottomStart: Dp,
    imageHeight: Dp,
    drink: UiDrinks,
    context: Context
){
    Row(modifier) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(drink.photo)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_error_placeholder),
            contentDescription = stringResource(R.string.cocktail_image),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(RoundedCornerShape(topStart, topEnd, bottomEnd, bottomStart))
                .fillMaxWidth()
                .height(imageHeight)
        )
    }
}
