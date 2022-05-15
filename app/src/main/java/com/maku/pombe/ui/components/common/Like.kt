package com.maku.pombe.ui.components.common

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Like(modifier: Modifier = Modifier, favoriteDrink: () -> Unit,){
    val colors = MaterialTheme.colors
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .size(80.dp)
            .clip(shape = CircleShape)
            .fillMaxWidth()
            .padding(20.dp)
            .background(Color(0xFFDDDDDD).copy(alpha = 0.9f), shape = CircleShape)
    ) {
        Image(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = "favorite",
            colorFilter = ColorFilter.tint(Color(0xFFFFA500)),
            modifier = modifier
                .size(30.dp)
                .clickable {
                    favoriteDrink()
                    Toast
                        .makeText(
                            context,
                            "Likes",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
        )
    }
}

@Composable
@Preview
fun LikePreview(modifier: Modifier = Modifier, favoriteDrink: () -> Unit = { }){
    Like(modifier, favoriteDrink)
}
