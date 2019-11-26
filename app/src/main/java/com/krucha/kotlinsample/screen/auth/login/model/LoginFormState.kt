package com.krucha.kotlinsample.screen.auth.login.model

import androidx.annotation.StringRes

data class LoginFormState(
    @StringRes val emailError: Int? = null,
    @StringRes val passwordError: Int? = null,
    val isDataValid: Boolean = false
)