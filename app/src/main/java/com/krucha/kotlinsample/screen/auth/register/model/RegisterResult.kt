package com.krucha.kotlinsample.screen.auth.register.model

data class RegisterResult(
    val success: RegisteredUser? = null,
    val error: Int? = null
)