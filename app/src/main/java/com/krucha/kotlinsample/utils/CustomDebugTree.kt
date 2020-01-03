package com.krucha.kotlinsample.utils

import android.util.Log
import timber.log.Timber

class CustomDebugTree : Timber.DebugTree() {
    private val prefix: String = "MyTag."

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Log.println(priority, prefix + tag, message)
    }
}