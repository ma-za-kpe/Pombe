package com.maku.pombe.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maku.logging.Logger
import com.maku.pombe.latestfeature.LatestDrinkEvent
import com.maku.pombe.latestfeature.LatestFragmentViewModel

@Composable
fun DrinkDetail(
  drinkId: String,
  upPress: () -> Unit
) {
  val latestFragmentViewModel = hiltViewModel<LatestFragmentViewModel>()
  val state = latestFragmentViewModel.state.value!!.drinkById
  Logger.d("by id LatestCard ${state.name}")

  Column() {
    Text(text ="drink")
    Text(text = latestFragmentViewModel.state.value!!.drinkById.name)
  }

}

