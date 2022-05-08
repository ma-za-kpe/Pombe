package com.maku.pombe.ui.components.popular

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink
import com.maku.pombe.ui.components.common.CardBottom
import com.maku.pombe.ui.components.common.CardImage
import com.maku.pombe.ui.components.common.CategoryCard

@Composable
fun PopularCard(
    drink: UIPopularDrink,
    modifier: Modifier = Modifier,
    favoriteDrink: () -> Unit
){
    val colors = MaterialTheme.colors
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = colors.background,
        contentColor = colors.onBackground,
        modifier = modifier.fillMaxWidth(),
    ) {
        ConstraintLayout(
            modifier = modifier.fillMaxSize(),
        ) {
            val (
                drinkImage, bottomCard, catCard
            ) = createRefs()

            CardImage(
                modifier
                    .width(80.dp)
                    .constrainAs(drinkImage) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
                topStart = 20.dp ,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
                bottomStart = 20.dp,
                imageHeight = 90.dp,
                drink = drink,
                context = context
            )

            CardBottom(modifier
                .constrainAs(bottomCard) {
                    start.linkTo(drinkImage.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(drinkImage.bottom)
                    width = Dimension.fillToConstraints
                },
                favoriteDrink,
                drink
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}
