package com.krucha.kotlinsample.features.auth

import com.krucha.kotlinsample.utils.Result

interface IRegister {
    suspend fun register(email: String, password: String): Result<AuthUser>
}