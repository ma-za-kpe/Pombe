package com.maku.pombe.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.maku.pombe.common.presentation.model.latest.UILatestDrink

@Composable
fun LatestCard(drink: UILatestDrink,
               modifier: Modifier = Modifier,
               favoriteDrink: () -> Unit) {
    val colors = MaterialTheme.colors
    val context = LocalContext.current
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
            backgroundColor = colors.background,
            contentColor = colors.onBackground,
            modifier = modifier.size(300.dp),
        ) {
            ConstraintLayout(
                modifier = modifier.fillMaxSize(),
            ) {
                val (
                    drinkImage, bottomCard, categoryCard, like
                ) = createRefs()

                CardImage(
                    modifier
                        .constrainAs(drinkImage) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                    topEnd = 20.dp ,
                    topStart = 20.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp,
                    imageHeight = 220.dp,
                    drink = drink,
                    context = context
                )

                CardBottom(modifier
                    .constrainAs(bottomCard) {
                        top.linkTo(drinkImage.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }, favoriteDrink, drink)

                CategoryCard(modifier = modifier.constrainAs(categoryCard) {
                    start.linkTo(drinkImage.start)
                    top.linkTo(drinkImage.top)
                    width = Dimension.fillToConstraints
                },  topEnd = 20.dp ,
                    topStart = 0.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 0.dp,drink.category, colors.onPrimary, Color(0xFFFFA500)
                )

                Like(modifier.constrainAs(like) {
                    end.linkTo(drinkImage.end)
                    top.linkTo(bottomCard.top)
                    bottom.linkTo(bottomCard.bottom)
                    width = Dimension.fillToConstraints
                },favoriteDrink)

            }
    }
}
