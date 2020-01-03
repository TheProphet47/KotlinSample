package com.krucha.kotlinsample.features.auth

import com.krucha.kotlinsample.di.scope.PerApp
import com.krucha.kotlinsample.utils.Result
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@PerApp
class LoginRepository @Inject constructor(private val service: ILogin)
    : AuthScope by AuthScopeImpl {

    init {
        Timber.d("LoginRep $service")
    }

    var user: AuthUser? = null
        private set

    val isAuth: Boolean
        get() = user != null


    suspend fun login(email: String, password: String) = withContext(authScope.coroutineContext) {
        val loginResult = service.login(email, password)

        if (loginResult is Result.Success) {
            user = loginResult.data
        }

        return@withContext loginResult
    }

    fun logout() {
        service.logout()
        user = null
    }
}