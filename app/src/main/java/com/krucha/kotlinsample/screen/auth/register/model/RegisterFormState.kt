package com.krucha.kotlinsample.screen.auth.register.model

import androidx.annotation.StringRes

data class RegisterFormState(
    @StringRes val nameError: Int? = null,
    @StringRes val emailError: Int? = null,
    @StringRes val passwordError: Int? = null,
    @StringRes val rePasswordError: Int? = null,
    val isDataValid: Boolean = false
)