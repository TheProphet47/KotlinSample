package com.krucha.kotlinsample.features.auth

data class AuthUser (
    val id: String,
    val email: String? = null,
    val name: String? = null
)