package com.maku.pombe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maku.logging.Logger
import com.maku.pombe.latestfeature.LatestDrinkEvent
import com.maku.pombe.latestfeature.LatestFragmentViewModel
import com.maku.pombe.popularfeature.presentation.PopularDrinkEvent
import com.maku.pombe.popularfeature.presentation.PopularFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val latestFragmentViewModel: LatestFragmentViewModel by viewModels()
    private val popularFragmentViewModel: PopularFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PombeApp()
            initData(latestFragmentViewModel, popularFragmentViewModel)
        }
    }

    private fun initData(
        latestFragmentViewModel: LatestFragmentViewModel,
        popularFragmentViewModel: PopularFragmentViewModel
    ) {
        latestFragmentViewModel.onEvent(LatestDrinkEvent.RequestLatestDrinksList)
         popularFragmentViewModel.onEvent(PopularDrinkEvent.RequestPopularDrinksList)
    }
}


