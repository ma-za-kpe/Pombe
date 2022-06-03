package com.maku.pombe.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavHostController
import com.google.accompanist.placeholder.placeholder
import com.maku.pombe.category.DrinkCategoryViewModel
import com.maku.pombe.category.DrinkCategoryViewState
import com.maku.pombe.latestfeature.LatestDrinkViewState
import com.maku.pombe.latestfeature.LatestFragmentViewModel
import com.maku.pombe.popularfeature.presentation.PopularFragmentViewModel
import com.maku.pombe.searchfeature.presentation.SearchViewModel
import com.maku.pombe.ui.components.latest.LatestCard
import com.maku.pombe.ui.components.latest.LatestCardPlaceHolder
import com.maku.pombe.ui.components.popular.PopularCardItem
import com.maku.pombe.ui.components.popular.PopularCardItemPlaceHolder
import com.maku.pombe.ui.components.search.PombeCategoryChip
import com.maku.pombe.ui.components.search.PombeCategoryChipPlaceHolder
import com.maku.pombe.ui.rememberPombeAppState
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeScreen(
    navController: NavHostController,
    latestFragmentViewModel: LatestFragmentViewModel,
    popularFragmentViewModel: PopularFragmentViewModel,
    drinkCategoryViewModel: DrinkCategoryViewModel,
    onItemClick: (String) -> Unit,
    searchViewModel: SearchViewModel
) {
    val context = LocalContext.current
    val appState = rememberPombeAppState()
    val scope = rememberCoroutineScope()
    val modifier = Modifier
    val state = latestFragmentViewModel.state.observeAsState()
    val categoryState = drinkCategoryViewModel.state.observeAsState()
    val popularState = popularFragmentViewModel.state.observeAsState()

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(16.dp)){
        ObserveCategoryPombeScreenState(categoryState.value, drinkCategoryViewModel, modifier, scope)
        Spacer(modifier = Modifier.height(8.dp))
        TitleItem("Latest",
            viewAll = {
                Toast.makeText(
                    context,
                    "More latest Pombe",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        ObserveLatestPombeScreenState(state.value!!, categoryState, onItemClick, searchViewModel, modifier)
        Spacer(modifier = Modifier.height(10.dp))
        TitleItem("Popular",
            viewAll = {
                Toast.makeText(
                    context,
                    "More Popular Pombe",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (popularState.value!!.loading){
//            LinearProgressIndicator(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(15.dp)
//            )
            PopularCardItemPlaceHolder(modifier)
        } else {
            popularState.value!!.drinks.forEach {drink ->
                if (categoryState.value!!.selectedCategory == "All"){
                    PopularCardItem(drink = drink, onItemClick = {}, likedItem = {})
                } else if(categoryState.value!!.selectedCategory == drink.category){
                    PopularCardItem(drink = drink, onItemClick = {}, likedItem = {})
                }
            }
        }
    }
}

@Composable
fun ObserveCategoryPombeScreenState(
    value: DrinkCategoryViewState?,
    drinkCategoryViewModel: DrinkCategoryViewModel,
    modifier: Modifier,
    scope: CoroutineScope
) {
    if (value!!.loading){
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(5) {
                PombeCategoryChipPlaceHolder(
                    modifier.placeholder(
                        visible = true,
                        color = Color.Gray,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(20.dp),
                    )
                )
            }
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(value.categories){ category ->
                    PombeCategoryChip(
                        setSelected = { name, selected ->
                            run {
                                drinkCategoryViewModel.setSelectedCategory(
                                    name,
                                    selected
                                )
                            }
                        },
                        selected = value.selectedCategory == category.name,
                        category = category.name,
                        scope,
                    ) {

                    }
                }
            }
        }
}

@Composable
fun ObserveLatestPombeScreenState(
    value: LatestDrinkViewState,
    categoryState: State<DrinkCategoryViewState?>,
    onItemClick: (String) -> Unit,
    searchViewModel: SearchViewModel,
    modifier: Modifier
) {
    if (value.loading){
//        LinearProgressIndicator(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(15.dp)
//        )
        LatestCardPlaceHolder(modifier)
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(value.drinks){ drink ->
                if (categoryState.value!!.selectedCategory == "All"){
                    LatestCard(
                        drink = drink,
                        favoriteDrink = { /*TODO*/ },
                        onItemClick = onItemClick,
                        searchViewModel = searchViewModel
                    )
                } else if(categoryState.value!!.selectedCategory == drink.category){
                    // if empty, handle that e.g show text or sth
                    LatestCard(
                        drink = drink,
                        favoriteDrink = { /*TODO*/ },
                        onItemClick = onItemClick,
                        searchViewModel = searchViewModel
                    )
                }

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
fun TitleItemPreview(viewAll: () -> Unit = { }) {
    TitleItem("Latest", viewAll)
}