package com.maku.logging

import android.util.Log
import timber.log.Timber

class TimberLogging : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when (priority) {
            Log.WARN -> logWarning(priority, tag, message)
            Log.ERROR -> logError(t, priority, tag, message)
        }
    }

    private fun logWarning(priority: Int, tag: String?, message: String) {
        // Log to external service like Crashlytics
    }

    private fun logError(t: Throwable?, priority: Int, tag: String?, message: String) {
        // Log to external service like Crashlytics

        t?.let {
            // Log to external service like Crashlytics
        }
    }
}
