package com.maku.pombe.ui.components.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink
import com.maku.pombe.ui.components.common.CardBottom
import com.maku.pombe.ui.components.common.CardImage

@Composable
fun FavoriteCard(
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
                bottomStart = 0.dp,
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

//            Card(
//                shape = RoundedCornerShape(
//                    0.dp,
//                    0.dp,
//                    20.dp,
//                    20.dp),
//                elevation = 10.dp,
//                backgroundColor =  Color(0xFFFFA500),
//                modifier = modifier.constrainAs(catCard) {
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    top.linkTo(bottomCard.bottom)
//                    width = Dimension.fillToConstraints
//                },
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.padding(8.dp)
//                ) {
//                    Text(
//                        fontSize = 16.sp,
//                        text = drink.category,
//                        style = MaterialTheme.typography.body2,
//                        color = colors.onPrimary
//                    )
//                    Spacer(Modifier.width(8.dp))
//                    Icon(
//                        imageVector = Icons.Outlined.Favorite,
//                        contentDescription = "favorite",
//                        modifier = modifier
//                            .size(30.dp)
//                            .clickable {
//                                favoriteDrink()
//                                Toast
//                                    .makeText(
//                                        context,
//                                        "Likes",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                    .show()
//                            }
//                    )
//                }
//            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}
