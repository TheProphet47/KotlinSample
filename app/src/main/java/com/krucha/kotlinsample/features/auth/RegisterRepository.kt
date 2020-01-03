package com.krucha.kotlinsample.features.auth

import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val service: IRegister)
    : AuthScope by AuthScopeImpl {

    init {
        Timber.d("RegisterRep $service")
    }

    suspend fun register(email: String, password: String) = withContext(authScope.coroutineContext) {
        return@withContext service.register(email, password)
    }
}