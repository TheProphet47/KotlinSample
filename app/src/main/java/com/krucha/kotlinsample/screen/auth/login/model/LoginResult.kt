package com.krucha.kotlinsample.screen.auth.login.model

data class LoginResult(
    val success: LoggedInUser? = null,
    val error: Int? = null
)