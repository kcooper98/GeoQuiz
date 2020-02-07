package com.csci448.kcooper.geoquiz

import android.app.Application
import android.content.res.Configuration
import android.content.res.Configuration.*
import android.util.Log

private const val LOG_TAG = "448.QuizApplication"

class QuizApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate() called")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(LOG_TAG, "onConfigurationChanged() called")
        Log.d(LOG_TAG, "orientation is ${
            when (newConfig?.orientation ?: ORIENTATION_UNDEFINED) {
                ORIENTATION_LANDSCAPE -> "Landscape"
                ORIENTATION_PORTRAIT -> "Portrait"
                else -> "Undefined"
            }
        }")
    }
}