package com.maku.pombe

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Pombe: Application() {

    override fun onCreate() {
        super.onCreate()
        //timber
        Timber.plant(Timber.DebugTree())
    }
}