package com.maku.pombe.ui.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.placeholder
import com.maku.logging.Logger
import com.maku.pombe.R
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
          val (image, name) = createRefs()
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
        }
      }
    }
  }
}

