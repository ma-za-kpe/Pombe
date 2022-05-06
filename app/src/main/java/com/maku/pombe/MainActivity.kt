package com.maku.pombe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.maku.logging.Logger
import com.maku.pombe.latestfeature.LatestDrinkEvent
import com.maku.pombe.latestfeature.LatestFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val latestFragmentViewModel: LatestFragmentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PombeApp()
            initData(latestFragmentViewModel)
        }
    }

    private fun initData(latestFragmentViewModel: LatestFragmentViewModel) {
        latestFragmentViewModel.onEvent(LatestDrinkEvent.RequestLatestDrinksList)
    }
}


