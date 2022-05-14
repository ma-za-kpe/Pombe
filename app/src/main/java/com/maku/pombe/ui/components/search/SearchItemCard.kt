package com.maku.pombe.ui.components.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.maku.pombe.common.presentation.model.latest.UILatestDrink
import com.maku.pombe.ui.components.common.CardBottom
import com.maku.pombe.ui.components.common.CardImage
import com.maku.pombe.ui.components.common.CategoryCard
import com.maku.pombe.ui.components.common.Like

@Composable
fun SearchItemCard(drink: UILatestDrink,
               modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colors
    val context = LocalContext.current
    Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
            backgroundColor = colors.background,
            contentColor = colors.onBackground,
        ) {
            ConstraintLayout(
                modifier = modifier.fillMaxSize(),
            ) {
                val (
                    drinkImage, drinkName
                ) = createRefs()

                CardImage(
                    modifier
                        .constrainAs(drinkImage) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                    topEnd = 20.dp,
                    topStart = 20.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp,
                    imageHeight = 120.dp,
                    drink = drink,
                    context = context
                )

                Text(
                    text = drink.name,
                    modifier.constrainAs(drinkName) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(drinkImage.bottom)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

            }
    }
}
