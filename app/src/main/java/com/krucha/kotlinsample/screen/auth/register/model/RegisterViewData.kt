package com.krucha.kotlinsample.screen.auth.register.model

import com.krucha.kotlinsample.data.model.User

data class RegisterViewData(
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var rePassword: String? = null,
    var gender: User.Gender = User.Gender.MAN
) {
    fun isFilled() = !name.isNullOrEmpty()
            && !email.isNullOrEmpty()
            && !password.isNullOrEmpty()
            && !rePassword.isNullOrEmpty()
}

