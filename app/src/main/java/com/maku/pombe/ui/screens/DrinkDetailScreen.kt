package com.maku.pombe.ui.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.maku.pombe.R
import com.maku.pombe.common.domain.model.shared.Ingredients
import com.maku.pombe.latestfeature.LatestDrinkEvent
import com.maku.pombe.latestfeature.LatestDrinkViewState
import com.maku.pombe.latestfeature.LatestFragmentViewModel

@Composable
fun DrinkDetailScreen(
  drinkId: String,
  upPress: () -> Unit
) {
  val latestFragmentViewModel = hiltViewModel<LatestFragmentViewModel>()
  LaunchedEffect(key1 = true, block = {
    latestFragmentViewModel.onEvent(LatestDrinkEvent.RequestLatestDrinkById, drinkId)
  })
  val state = latestFragmentViewModel.state.observeAsState()
  DrinkDetail(state)
}

@Composable
fun DrinkDetail(state: State<LatestDrinkViewState?>) {
  Box(Modifier.fillMaxSize()) {
    val scroll = rememberScrollState(0)
    Body(scroll, state)
  }
}

@Composable
fun Body(scroll: ScrollState, state: State<LatestDrinkViewState?>) {
  val context = LocalContext.current
  val drink = state.value!!.drinkById
  val tags: List<String> = drink.strTags.split(",").map { it.trim() }

  Column(
      modifier = Modifier.verticalScroll(scroll)
    ) {
    Surface(Modifier.fillMaxWidth()) {
      Column {
        ConstraintLayout(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)

        ) {
          val (image, name, tag, mi) = createRefs()
          AsyncImage(
            model = ImageRequest.Builder(context)
              .data(drink.photo)
              .crossfade(true)
              .build(),
            placeholder = painterResource(R.drawable.ic_error_placeholder),
            contentDescription = stringResource(R.string.cocktail_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .size(200.dp)
              .constrainAs(image) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
              }
              .clip(RoundedCornerShape(20.dp)),
          )

          Text(
            text = drink.name,
            Modifier.constrainAs(name) {
              top.linkTo(image.bottom,8.dp)
              start.linkTo(image.start)
              end.linkTo(image.end)
              width = Dimension.wrapContent
            },
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign= TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
          )
            LazyRow(
              Modifier.constrainAs(tag) {
                top.linkTo(name.bottom,8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
              }
            ) {
              item {
                Text(
                  text = "TAGS: ",
                  style = MaterialTheme.typography.subtitle1,
                )
              }
              items(tags){
                if (it.isNotEmpty()){
                  TagsChip(it)
                } else {
                  Text(
                    text = "No TAGS AVAILABLE ",
                    style = MaterialTheme.typography.subtitle1,
                  )
                }
              }
            }
//          MeasureIngredients(
//            Modifier.constrainAs(mi) {
//              top.linkTo(tag.bottom,8.dp)
//              start.linkTo(parent.start)
//              end.linkTo(parent.end)
//              width = Dimension.wrapContent
//            },
//            drink.details.ingredients
//          )
        }
      }
    }
  }
}

@Composable
fun TagsChip(tag: String) {
  val color = MaterialTheme.colors
  Surface(
    modifier = Modifier.padding(end = 8.dp)
      .clip(RoundedCornerShape(20.dp)),
    elevation = 8.dp,
    shape = MaterialTheme.shapes.small,
    color = color.primary,
  ) {
    Box{
      Text(
        text = tag,
        style = MaterialTheme.typography.caption,
        maxLines = 1,
        modifier = Modifier
          .padding(
            horizontal = 20.dp,
            vertical = 6.dp),
        color = Color.White
      )
    }
  }
}

@Composable
fun MeasureIngredients(modifier: Modifier, ingredients: List<Ingredients>) {
  LazyColumn() {
    item {
      Text(
        text = "Ingredients",
        style = MaterialTheme.typography.subtitle1,
      )
    }
    items(ingredients){
      if (it.toString().isNotEmpty()){
        Text(
          text = it.toString(),
          style = MaterialTheme.typography.subtitle1,
        )
      } else {
        Text(
          text = "No Ingredients AVAILABLE ",
          style = MaterialTheme.typography.subtitle1,
        )
      }
    }
  }
}

