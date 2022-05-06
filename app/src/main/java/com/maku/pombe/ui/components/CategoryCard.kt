package com.maku.pombe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    topEnd: Dp,
    topStart: Dp,
    bottomEnd: Dp,
    bottomStart: Dp,
    category: String,
    onPrimary: Color,
    bgColor: Color
) {
    Card(
        shape = RoundedCornerShape(topEnd, topStart, bottomEnd, bottomStart),
        elevation = 10.dp,
        backgroundColor = bgColor,
        contentColor = onPrimary
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .wrapContentSize()
                .padding(10.dp)
        ) {
            Image(
                imageVector = Icons.Filled.Menu,
                colorFilter = ColorFilter.tint(onPrimary),
                contentDescription = "Cocktail category",
            )
            Spacer(Modifier.width(8.dp))
            Text(
                fontSize = 16.sp,
                text = category,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}
