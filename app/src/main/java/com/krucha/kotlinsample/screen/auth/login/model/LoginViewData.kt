package com.krucha.kotlinsample.screen.auth.login.model

data class LoginViewData(
    var email: String? = null,
    var password: String? = null
) {
    fun isFilled() = !email.isNullOrEmpty() && !password.isNullOrEmpty()
}