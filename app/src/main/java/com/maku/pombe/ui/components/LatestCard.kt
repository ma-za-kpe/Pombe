package com.maku.pombe.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.maku.pombe.R

@Composable
fun LatestCard(
    modifier: Modifier = Modifier,
    category: String,
    imgUrl: String,
    drinkName: String,
    strIngredients: String,
    favoriteDrink: () -> Unit
){
    val colors = MaterialTheme.colors
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
            backgroundColor = colors.background,
            contentColor = colors.onBackground,
            modifier = modifier.height(380.dp),
        ) {
            ConstraintLayout(
                modifier = modifier.fillMaxSize(),
            ) {
                val (
                    drinkImage, titleText, ingredientText, timeCard, likeCard
                ) = createRefs()
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imgUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_error_placeholder),
                    contentDescription = stringResource(R.string.cocktail_image),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .constrainAs(drinkImage) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .height(300.dp)
                )

                CategoryCard(modifier = modifier.constrainAs(timeCard) {
                    start.linkTo(drinkImage.start)
                    top.linkTo(drinkImage.top)
                    width = Dimension.fillToConstraints
                }, category)

                Text(
                    text = drinkName,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign= TextAlign.Start,
                    modifier = modifier.constrainAs(titleText) {
                        top.linkTo(drinkImage.bottom, 8.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(likeCard.start, 16.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                Text(
                    text = "$strIngredients ingredients",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign= TextAlign.Start,
                    modifier = modifier.constrainAs(ingredientText) {
                        top.linkTo(titleText.bottom)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(likeCard.start, 16.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                Like(modifier = modifier.constrainAs(likeCard) {
                    end.linkTo(drinkImage.end)
                    bottom.linkTo(parent.bottom)
                }, favoriteDrink)
            }
    }
}

@Composable
fun CategoryCard(modifier: Modifier = Modifier, category: String) {
    val colors = MaterialTheme.colors
    Card(
        shape = RoundedCornerShape(20.dp, 0.dp, 20.dp, 0.dp),
        elevation = 10.dp,
        backgroundColor = Color(0xFFFFA500),
        contentColor = colors.onPrimary
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
                colorFilter = ColorFilter.tint(colors.onPrimary),
                contentDescription = "Clock face",
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

@Composable
fun Like(modifier: Modifier = Modifier, favoriteDrink: () -> Unit,){
    val colors = MaterialTheme.colors
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            .size(100.dp)
            .clip(shape = CircleShape)
            .fillMaxWidth()
            .padding(20.dp)
            .background(Color(0xFFDDDDDD).copy(alpha = 0.9f), shape = CircleShape)
    ) {
        Image(
            imageVector = Icons.Outlined.Favorite,
            contentDescription = "favorite",
            colorFilter = ColorFilter.tint(colors.onPrimary),
            modifier = modifier
                .size(40.dp)
                .clickable {
                favoriteDrink()
                Toast.makeText(context,
                    "Likes",
                    Toast.LENGTH_SHORT).show()
            }
        )
    }
}

//@Composable
//fun RoundedIconButton(
//    tag: Tag,
//    asset: VectorAsset,
//    text: String,
//    background: Color = lightThemeColors.primary
//) {
//    Column(
//        modifier = Modifier.tag(tag) + Modifier.padding(16.dp),
//        horizontalGravity = Alignment.CenterHorizontally
//    ) {
//        Image(
//            modifier = Modifier.drawBackground(
//                background,
//                CircleShape
//            ) + Modifier.padding(20.dp),
//            asset = asset
//        )
//        Text(
//            modifier = Modifier.padding(top = 8.dp),
//            style = MaterialTheme.typography.body2,
//            color = Color.Gray,
//            text = text
//        )
//    }
//}


@Composable
@Preview
fun LatestCardPreview(modifier: Modifier = Modifier,favoriteDrink: () -> Unit = { }){
    LatestCard(modifier, "Cocktail",
        "https://www.thecocktaildb.com/images/media/drink/4vobt21643844913.jpg",
        "Cocktail Horse’s Neck",
        "8", favoriteDrink)
}

@Composable
@Preview
fun CategoryCardPreview(modifier: Modifier = Modifier){
    CategoryCard(modifier, "Cocktail")
}

@Composable
@Preview
fun LikePreview(modifier: Modifier = Modifier, favoriteDrink: () -> Unit = { }){
    Like(modifier, favoriteDrink)
}