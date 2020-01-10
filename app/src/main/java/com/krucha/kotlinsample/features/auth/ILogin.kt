package com.krucha.kotlinsample.features.auth

import com.krucha.kotlinsample.utils.Result

interface ILogin {
    val isAuth: Boolean
    val user: AuthUser?

    suspend fun login(email: String, password: String): Result<AuthUser>
    fun logout()
}