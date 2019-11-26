package com.krucha.kotlinsample

import android.util.Log

open class CustomLog (private val prefix: String) {

    companion object {
        private const val TAG = "MyTag"
    }

    fun debug(msg: String) {
        Log.d(TAG + prefix, msg)
    }
}