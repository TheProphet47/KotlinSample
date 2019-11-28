package com.krucha.kotlinsample.screen.auth.register.model

import androidx.annotation.StringRes

sealed class RegisterResult {

    data class Success(val user: RegisteredUser) : RegisterResult()
    data class Error(@StringRes val error: Int) : RegisterResult()
}