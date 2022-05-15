package com.maku.pombe.ui.components.popular

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.maku.pombe.R
import com.maku.pombe.common.presentation.model.popular.UIPopularDrink

//@Composable
//fun PopularCard(){
//
//}

@Composable
fun PopularCardItem(
    drink: UIPopularDrink,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    likedItem: (String) -> Unit)
{

    val color = MaterialTheme.colors
    val context = LocalContext.current

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(drink.id) }
            .background(color.background)
            .padding(horizontal = 4.dp)

    ) {
        val (image, name, tag, priceSpacer, price, remove, quantity) = createRefs()
        createVerticalChain(name, tag, priceSpacer, price, chainStyle = ChainStyle.Packed)
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(drink.photo)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_error_placeholder),
            contentDescription = stringResource(R.string.cocktail_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                }
                .clip(CircleShape)
        )

        Text(
            text = drink.name,
            style = MaterialTheme.typography.subtitle1,
            color = color.onBackground,
            modifier = Modifier.constrainAs(name) {
                linkTo(
                    start = image.end,
                    startMargin = 16.dp,
                    end = remove.start,
                    endMargin = 16.dp,
                    bias = 0f
                )
            }
        )
        IconButton(
            onClick = { likedItem(drink.id) },
            modifier = Modifier
                .constrainAs(remove) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .padding(top = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                tint = Color(0xFFFFA500),
                contentDescription = "like item"
            )
        }
        Text(
            text = drink.alcoholic.toString(),
            style = MaterialTheme.typography.body1,
            color = Color.Gray,
            modifier = Modifier.constrainAs(tag) {
                linkTo(
                    start = image.end,
                    startMargin = 16.dp,
                    end = parent.end,
                    endMargin = 16.dp,
                    bias = 0f
                )
            }
        )
        Spacer(
            Modifier
                .height(8.dp)
                .constrainAs(priceSpacer) {
                    linkTo(top = tag.bottom, bottom = price.top)
                }
        )
        Text(
            text = drink.category,
            style = MaterialTheme.typography.subtitle1,
            color =  Color(0xFFFFA500),
            modifier = Modifier.constrainAs(price) {
                linkTo(
                    start = image.end,
                    end = quantity.start,
                    startMargin = 16.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
            }
        )
    }
}