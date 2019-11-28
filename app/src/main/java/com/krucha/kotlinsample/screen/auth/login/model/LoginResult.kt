package com.krucha.kotlinsample.screen.auth.login.model

import androidx.annotation.StringRes

sealed class LoginResult {

    data class Success(val user: LoggedInUser) : LoginResult()
    data class Error(@StringRes val error: Int) : LoginResult()
}