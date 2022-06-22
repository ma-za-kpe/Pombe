package com.maku.pombe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
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
        setTheme(R.style.Theme_Pombe)
        super.onCreate(savedInstanceState)
        setContent {
            PombeApp()
        }

        println("13"+5+3)

        // TODO: find a better way of doing this: something like app start up and a side effect
        initData(latestFragmentViewModel, popularFragmentViewModel)
    }

    private fun initData(
        latestFragmentViewModel: LatestFragmentViewModel,
        popularFragmentViewModel: PopularFragmentViewModel
    ) {
        latestFragmentViewModel.onEvent(LatestDrinkEvent.RequestLatestDrinksList)
        popularFragmentViewModel.onEvent(PopularDrinkEvent.RequestPopularDrinksList)
    }
}


