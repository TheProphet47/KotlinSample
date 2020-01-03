package com.krucha.kotlinsample.features.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

interface AuthScope {
    val authScope: CoroutineScope
}

object AuthScopeImpl : AuthScope {
    override val authScope = CoroutineScope(Dispatchers.IO + Job())
}