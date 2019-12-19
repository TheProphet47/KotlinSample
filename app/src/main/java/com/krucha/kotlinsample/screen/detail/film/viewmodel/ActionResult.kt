package com.krucha.kotlinsample.screen.detail.film.viewmodel

import androidx.annotation.StringRes

sealed class ActionResult {

    data class Update(@StringRes val msg: Int) : ActionResult()
    class Remove : ActionResult()
}