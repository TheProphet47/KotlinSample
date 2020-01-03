package com.krucha.kotlinsample.screen.auth.login.model

import androidx.annotation.StringRes

data class LoggedInUser(
    val name: String?,
    @StringRes val message: Int
)