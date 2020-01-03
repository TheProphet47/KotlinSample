package com.krucha.kotlinsample.features.auth

import com.krucha.kotlinsample.utils.Result

interface ILogin {
    suspend fun login(email: String, password: String): Result<AuthUser>
    fun logout()
}