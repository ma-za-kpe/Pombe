package com.maku.pombe

import android.app.Application
import com.maku.logging.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Pombe: Application() {

    // initiate analytics, crashlytics, etc

    override fun onCreate() {
        super.onCreate()

        initLogger()
    }

    private fun initLogger() {
        Logger.init()
    }
}