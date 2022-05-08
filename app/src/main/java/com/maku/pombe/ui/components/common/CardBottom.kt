package com.maku.pombe.ui.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maku.pombe.common.presentation.model.UiDrinks
import com.maku.pombe.common.presentation.model.latest.UILatestDrink

@Composable
fun CardBottom(modifier: Modifier = Modifier, favoriteDrink: () -> Unit, drink: UiDrinks){
        Row(
            modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,) {
            Column {
                Text(
                    text = drink.name,
                    modifier.width(190.dp),
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign= TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Text(
                    text = drink.alcoholic.toString(),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign= TextAlign.Start,
                )
            }
        }
}
