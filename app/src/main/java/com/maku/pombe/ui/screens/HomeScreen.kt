package com.maku.pombe.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maku.logging.Logger
import com.maku.pombe.common.presentation.model.latest.UILatestDrink
import com.maku.pombe.latestfeature.LatestDrinkViewState
import com.maku.pombe.latestfeature.LatestFragmentViewModel
import com.maku.pombe.ui.components.LatestCard
import com.maku.pombe.ui.components.LoadingLatestPombeListShimmer

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val latestFragmentViewModel: LatestFragmentViewModel = viewModel()
    val state =  latestFragmentViewModel.state.observeAsState()
    Column(
        modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
    ) {
        // TODO: 1. carousel
        // TODO: 2. Category
        // TODO: 4. Latest view all
        TitleItem("Latest",
            viewAll = { Toast.makeText(context,
                "More latest Pombe",
                Toast.LENGTH_SHORT).show()
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        // TODO: 5. Latest List
        ObserveLatestPombeScreenState(state.value!!)
        // TODO: 6. Popular view all
        Spacer(modifier = Modifier.height(10.dp))
        TitleItem("Popular",
            viewAll = { Toast.makeText(context,
                "More Popular Pombe",
                Toast.LENGTH_SHORT).show()
            }
        )
        // TODO: 7. Popular List
    }
}

//@Composable
//fun ObserveLatestPombeScreenState(value: LatestDrinkViewState) {
//    val drinks: List<UILatestDrink> = value.drinks
//    Logger.d("Got more drinks! $drinks")
//
//    LazyRow(
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        ) {
//        items(drinks){ drink ->
//           LatestCard(drink = drink) {
//
//           }
//        }
//    }
//}

@Composable
fun ObserveLatestPombeScreenState(value: LatestDrinkViewState) {
    if (value.loading){
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
    // LoadingLatestPombeListShimmer(imageHeight = 220.dp)
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(value.drinks){ drink ->
                LatestCard(drink = drink) {}
            }
        }
    }
}

@Composable
fun TitleItem(title: String, viewAll: () -> Unit) {
    val context = LocalContext.current
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val (
                titleRef, viewall
            ) = createRefs()

            Text(
                text = title,
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign= TextAlign.Start,
                modifier = Modifier
                    .constrainAs(titleRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = "View all",
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign= TextAlign.Start,
                modifier = Modifier
                    .constrainAs(viewall) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .clickable {
                        viewAll()
                        Toast
                            .makeText(
                                context,
                                "View all",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
            )
        }
}

@Composable
@Preview
fun PombeCardPreview() {
    HomeScreen()
}

@Composable
@Preview
fun TitleItemPreview(viewAll: () -> Unit = { }) {
    TitleItem("Latest", viewAll)
}